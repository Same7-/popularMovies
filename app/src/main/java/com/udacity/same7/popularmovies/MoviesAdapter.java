package com.udacity.same7.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by same7.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {
    private List<MovieModel> moviesList;
    private Context context;
    private ImageLoader imageLoader;
    private Activity mainActivity;

    public MoviesAdapter(Context context,List<MovieModel>list,Activity mainActivity){
        this.moviesList = list;
        this.context = context;
        this.mainActivity = mainActivity;
        this.imageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false);
        return new MovieHolder(layoutView,context,moviesList,mainActivity);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
       // holder.movieName.setText(moviesList.get(position).getMovieName());
      //  holder.movieRate.setText(moviesList.get(position).getMovieRate());
        holder.movieCover.setImageUrl(moviesList.get(position).getMovieCover(),imageLoader);

    }

    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }
}
