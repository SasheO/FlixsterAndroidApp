package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel // makes the movie class parcelable
public class Movie {
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double voteAverage;
    String originalLanguage;

    // no arg empty constructor required for parceler
    public Movie() {}


    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = "https://image.tmdb.org/t/p/w342" + jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = "https://image.tmdb.org/t/p/w342" + jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
        originalLanguage = "Lang: "  + jsonObject.getString("original_language");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<movieJsonArray.length(); i++){
        movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath(){
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
