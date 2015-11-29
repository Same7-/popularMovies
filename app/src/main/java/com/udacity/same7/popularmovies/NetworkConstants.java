package com.udacity.same7.popularmovies;

/**
 * Created by same7.
 */
public class NetworkConstants {
    //network request
    public final static String _END_POINT = "http://api.themoviedb.org/3/discover/movie";
    public final  static String _POPULARITY = "popularity";
    public final  static String _Rate = "vote_average";
    public final  static String _DESC = ".desc";
    public final  static String _QUESTION_MARK = "?";
    public final  static String _AMPERSAND = "&";
    public final  static String EQUAL = "=";
    public final  static String _API_KEY = "api_key";
    public final  static String _SORT_BY = "sort_by";
    public final  static String PAGE = "page";

    //json result
    public static final String JSON_RESULTS = "results";
    public static final String JSON_TITLE = "title";
    public static final String JSON_COVER = "http://image.tmdb.org/t/p/w185//";
    public static final String JSON_VOTE = "vote_average";
    public static final String JSON_OVERVIEW = "overview";
    public static final String JSON_DATE = "release_date";
    public static final String JSON_POSTER_PATH = "poster_path";

    //movie bundle
    public static final String BUNDLE_COVER = "cover";
    public static final String BUNDLE_NAME = "name";
    public static final String BUNDLE_OVERVIEW = "overview";
    public static final String BUNDLE_DATE = "releaseDate";
    public static final String BUNDLE_RATE = "rate";



}
