package com.vsc.androidexam2020.data.remote.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrdersService {

    String BASE_URL = "https://us-central1-vsc-android-exam-2020.cloudfunctions.net/";

    @POST("calculate")
    Call<OrderResponse> calculateOrder(@Body OrderRequest request);

    @POST("makeOrder")
    Call<OrderResponse> makeOrder(@Body OrderRequest request);

    @POST("getAllOrders")
    Call<List<OrderResponse>> getAllOrders(@Body OrderRequest request);
}
