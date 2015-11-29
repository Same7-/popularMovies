package com.udacity.same7.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String movieCover;
    private String movieName;
    private String movieOverview;
    private String movieDate;
    private String movieRate;
    private ImageLoader imageLoader;
    private Button mRate;
    private TextView mDate;
    private TextView mOverview;
    private TextView mMovieName;
    private NetworkImageView cover;
    private  final String STRING_NULL = "null";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnDetailFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           getDataView(getArguments());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View detailView =   inflater.inflate(R.layout.fragment_detail, container, false);
        mRate = (Button)detailView.findViewById(R.id.rate);
        mDate = (TextView)detailView.findViewById(R.id.release_date);
        mOverview = (TextView)detailView.findViewById(R.id.synopsis);
        cover = ( NetworkImageView)detailView.findViewById(R.id.backdrop);
        mMovieName = (TextView)detailView.findViewById(R.id.movie_name);
        if(getArguments()!=null){/*one pane*/
                Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().onBackPressed();
                    }
                });


            setDataView();
        }

        if(savedInstanceState!=null) {
            updateView(savedInstanceState);
        }

        return detailView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
    }

    public void updateView(Bundle bundle){
        getDataView(bundle);
        setDataView();
    }

    public void getDataView(Bundle bundle){
        imageLoader =  VolleySingleton.getInstance(getActivity()).getImageLoader();
        movieCover = bundle.getString(NetworkConstants.BUNDLE_COVER,STRING_NULL);
        movieName = bundle.getString(NetworkConstants.BUNDLE_NAME,STRING_NULL);
        movieOverview = bundle.getString(NetworkConstants.BUNDLE_OVERVIEW,STRING_NULL);
        movieDate = bundle.getString(NetworkConstants.BUNDLE_DATE,STRING_NULL);
        movieRate = bundle.getString(NetworkConstants.BUNDLE_RATE,STRING_NULL);

    }

    public void setDataView(){
        if(!movieRate.equals(STRING_NULL))
            mRate.setText(movieRate);
        if(!movieDate.equals(STRING_NULL))
            mDate.setText( movieDate);
        if(!movieOverview.equals(STRING_NULL))
            mOverview.setText(movieOverview);
        if(!movieName.equals(STRING_NULL))
            mMovieName.setText(movieName);
        if(!movieCover.equals(STRING_NULL))
            cover.setImageUrl(movieCover,imageLoader);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NetworkConstants.BUNDLE_COVER, movieCover);
        outState.putString(NetworkConstants.BUNDLE_NAME, movieName);
        outState.putString(NetworkConstants.BUNDLE_OVERVIEW,movieOverview);
        outState.putString(NetworkConstants.BUNDLE_DATE, movieDate);
        outState.putString(NetworkConstants.BUNDLE_RATE,movieRate);
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
    public interface OnDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Bundle bundle);

    }


}
