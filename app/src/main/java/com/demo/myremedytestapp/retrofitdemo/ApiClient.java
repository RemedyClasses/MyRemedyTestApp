package com.demo.myremedytestapp.retrofitdemo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private static final String BASE_URL_2 = "https://reviewindia.com/api/";
    private static Retrofit retrofit = null;
    private static ApiClient mInstance;

    //Initialization of Retrofit class
    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    public static synchronized ApiClient getInsance(){
        if(mInstance == null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public ApiInterface getApi(){
        if(retrofit==null){
            return getClient().create(ApiInterface.class);
        }else {
            return retrofit.create(ApiInterface.class);
        }
    }

}
