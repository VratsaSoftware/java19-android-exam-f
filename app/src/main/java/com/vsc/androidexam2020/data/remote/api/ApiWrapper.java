package com.vsc.androidexam2020.data.remote.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsc.androidexam2020.FileUtils;
import com.vsc.androidexam2020.data.local.Order;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiWrapper {

    private static ApiWrapper instance;
    private final OrdersService service;


    public static ApiWrapper getInstance() {
        if(instance == null) instance = new ApiWrapper();
        return instance;
    }

    private ApiWrapper() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OrdersService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        service = retrofit.create(OrdersService.class);
    }

    public void calculateOrder(String fileName, long fileSizeInBytes, DataListener<Order> listener) {
        // TODO Ex 6
    }

    public void makeOrder(String userId, String fileName, long fileSizeInBytes, DataListener<Order> listener) {
        // TODO Ex 6
    }

    public void getAllOrders(String userId, DataListener<List<Order>> listener) {
        // TODO Ex 6
    }

    public interface DataListener<T> {
        void onDataReceived(T data);
    }
}
