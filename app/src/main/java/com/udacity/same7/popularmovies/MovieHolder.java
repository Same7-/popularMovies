package com.udacity.same7.popularmovies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by same7.
 */
public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public NetworkImageView movieCover;
    private List<MovieModel>movies;
    private Context context;
    private Activity mainActivity;

    public MovieHolder(View itemView,Context context,List<MovieModel>movies,Activity mainActivity) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.mainActivity = mainActivity;
        this.movies = movies;
        movieCover = (NetworkImageView)itemView.findViewById(R.id.movie_cover);
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        DetailFragment detailFragment = new DetailFragment();
        FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
        DetailFragment detailView = (DetailFragment)
                mainActivity.getFragmentManager().findFragmentById(R.id.detail_fragment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailFragment.setEnterTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.explode));
        }

        if(detailView==null) {/*One pane*/
                commitDetailFrag(detailFragment,transaction);
            }
            else{ /*Two panes*/
                detailView.updateView(putMovieBundle());
            }


    }

    public Bundle putMovieBundle(){
        int position = MovieHolder.this.getAdapterPosition();
        MovieModel movie = movies.get(position);
        Bundle movieDetails = new Bundle();
        movieDetails.putString(NetworkConstants.BUNDLE_COVER, movie.getMovieCover());
        movieDetails.putString(NetworkConstants.BUNDLE_NAME, movie.getMovieName());
        movieDetails.putString(NetworkConstants.BUNDLE_OVERVIEW, movie.getMovieOverview());
        movieDetails.putString(NetworkConstants.BUNDLE_RATE, movie.getMovieRate());
        movieDetails.putString(NetworkConstants.BUNDLE_DATE, movie.getReleaseDate());
        return movieDetails;
    }

    public void commitDetailFrag(DetailFragment detailFragment,FragmentTransaction transaction){
        transaction.replace(R.id.main_frame, detailFragment);
        transaction.addToBackStack(null);
        detailFragment.setArguments(putMovieBundle());
        transaction.commit();
    }



}
