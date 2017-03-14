package com.example.test.googlemapsactivity.activity;

import com.example.test.googlemapsactivity.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/*import com.squareup.okhttp.Call;

import retrofit.http.GET;
import retrofit.http.Query;*/

/**
 * Created by test on 17/2/17.
 */
public interface RetrofitMaps {
    @GET("api/directions/json")
    Call<Example> getDistanceDuration(@Query("origin") String origin, @Query("destination") String destination,@Query("api_key") String apiKey);
    /*Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode,@Query("api_key") String apiKey);*/
}
