package com.example.breakingnews.api;

import com.example.breakingnews.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("/top-headlines")
    Call<BaseResponse> getNewsByCategory(@Query("apiKey") String apiKey,
                                         @Query("country") String country,
                                         @Query("category") String category);
}
