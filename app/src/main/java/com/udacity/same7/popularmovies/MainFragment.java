package com.udacity.same7.popularmovies;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnMainFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMainFragmentInteractionListener mListener;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<MovieModel> moviesData;
    private RecyclerView mainGrid;
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private int pageNo = 1;
    private String sortBy = NetworkConstants._POPULARITY;
    private MoviesAdapter adapter;
    private final String STATE_MOVIES = "state_movies";
    private final String ITEM_POSITION = "itemPosition";
    private final String SORTED = "sorted";
    private final String PAGE_NO = "pageNo";
    private CoordinatorLayout mainLayout;
    private Snackbar snackbar;
    private ProgressBar progressBar;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState!=null){
            moviesData = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            savedInstanceState.getInt(ITEM_POSITION, 1);
            savedInstanceState.getString(SORTED, sortBy);
            savedInstanceState.getInt(PAGE_NO, 1);
        }
        else {
            moviesData = new ArrayList<>();
            getMovies(pageNo, sortBy);
        }


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setTransition();
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        mainGrid = (RecyclerView)mainView.findViewById(R.id.movies_grid);
        mainGrid.addItemDecoration(new GridSpace(getResources().getInteger(R.integer.grid_space)));
        adapter = new MoviesAdapter(getActivity(),moviesData,getActivity());

        int columns = getResources().getInteger(R.integer.columns);
        gridLayoutManager  = new GridLayoutManager(getActivity(),columns);

        mainGrid.setLayoutManager(gridLayoutManager);
        mainGrid.setAdapter(adapter);

        loadMore();
        return mainView;

    }



    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        progressBar = (ProgressBar)getActivity().findViewById(R.id.loading_spinner);
        mainLayout = (CoordinatorLayout)getActivity().findViewById(R.id.main_content);
        toolbar.setTitle(R.string.movies);
    }

    public void getMovies(final int pageNo, final String sortBy){

        String url=  NetworkConstants._END_POINT
                + NetworkConstants._QUESTION_MARK
                + NetworkConstants._SORT_BY+NetworkConstants.EQUAL+ sortBy
                + NetworkConstants._DESC + NetworkConstants._AMPERSAND
                +NetworkConstants.PAGE+NetworkConstants.EQUAL+pageNo
                +NetworkConstants._AMPERSAND
                + NetworkConstants._API_KEY +NetworkConstants.EQUAL+ Constants.KEY;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                ,url
                ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseJson(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof NoConnectionError || error instanceof TimeoutError) {
                             snackbar = Snackbar
                                    .make(mainLayout, R.string.connection, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getMovies(pageNo, sortBy);
                                        }
                                    });

                            snackbar.show();
                        }
                        else {
                            Log.e(getString(R.string.volley_error), error.getMessage());
                        }
                    }
                }
        );
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addRequest(request);
    }
    public void parseJson(JSONObject jsonObject) throws JSONException {
        progressBar.setVisibility(View.VISIBLE);
        JSONArray result = jsonObject.getJSONArray(NetworkConstants.JSON_RESULTS);

        for(int x=0;x<result.length();x++) {
            JSONObject jsonMovie = result.getJSONObject(x);
            MovieModel movie = new MovieModel();
            movie.setMovieName(jsonMovie.getString(NetworkConstants.JSON_TITLE));
            movie.setMovieCover(NetworkConstants.JSON_COVER + jsonMovie.getString(NetworkConstants.JSON_POSTER_PATH));
            movie.setMovieRate(jsonMovie.getString(NetworkConstants.JSON_VOTE));
            movie.setMovieOverview(jsonMovie.getString(NetworkConstants.JSON_OVERVIEW));
            movie.setReleaseDate(jsonMovie.getString(NetworkConstants.JSON_DATE));
            moviesData.add(movie);
        }
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
    }

    public void loadMore(){
        mainGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        pageNo++;
                        getMovies(pageNo, sortBy);
                    }
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, moviesData);
        outState.putInt(ITEM_POSITION, pastVisibleItems);
        outState.putString(SORTED, sortBy);
        outState.putInt(PAGE_NO, pageNo);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.popularity) {
            sortBy = NetworkConstants._POPULARITY;
        }
        else if(id == R.id.highestRate){
            sortBy = NetworkConstants._Rate;
        }
        moviesData.clear();
        adapter.notifyDataSetChanged();
        getMovies(1, sortBy);
        mainGrid.smoothScrollToPosition(1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMainFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMainFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Bundle bundle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setTransition(){
    //    this.setReenterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));
    }

}
