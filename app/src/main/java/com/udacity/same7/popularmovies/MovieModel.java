package com.udacity.same7.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by same7.
 */
public class MovieModel implements Parcelable {
    private String movieCover;
    private String movieName;
    private String movieRate;
    private String movieOverview;
    private String releaseDate;


    public String getMovieCover() {
        return movieCover;
    }

    public void setMovieCover(String movieCover) {
        this.movieCover = movieCover;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieCover);
        dest.writeString(this.movieName);
        dest.writeString(this.movieRate);
        dest.writeString(this.movieOverview);
        dest.writeString(this.releaseDate);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.movieCover = in.readString();
        this.movieName = in.readString();
        this.movieRate = in.readString();
        this.movieOverview = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
