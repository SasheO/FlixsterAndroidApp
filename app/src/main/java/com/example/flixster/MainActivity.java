package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Adapter.MovieAdapter;
import com.example.flixster.models.Movie;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String TAG = "MainActivity";
    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies );
        movies = new ArrayList<>();

        // create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set the adapter on recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a Layout manager on the adapter view
        rvMovies.setLayoutManager(new LinearLayoutManager(this ));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING + getString(R.string.API_KEY), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                 Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged(); // you must notify the adapter any time that the data set an adapter refers to is modified. Initially, the movies set was empty but now, it is populated with data from the API.
                    Log.d(TAG, "movies: " + movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}