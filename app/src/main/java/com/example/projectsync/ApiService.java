package com.example.projectsync;

import com.example.projectsync.model.PredictionRequest;
import com.example.projectsync.model.PredictionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("predict")
    Call<PredictionResponse> predict(@Body PredictionRequest request);
}