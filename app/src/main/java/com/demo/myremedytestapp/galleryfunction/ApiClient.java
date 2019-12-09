package com.demo.myremedytestapp.galleryfunction;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static ApiClient mApiClient;
    public static Retrofit mRetrofit = null;

    public static synchronized ApiClient getInstance(){
        if(mApiClient==null){
            mApiClient = new ApiClient();
        }

        return mApiClient;
    }


    public Retrofit getClient(){
        //Interceptor to log http calling data
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //To Handle Http connections
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(240, TimeUnit.SECONDS) //4-Minutes
                .connectTimeout(240, TimeUnit.SECONDS) //4-Minutes
                .writeTimeout(240, TimeUnit.SECONDS) //4-Minutes
                .addInterceptor(loggingInterceptor)
                .build();

        //Initialize Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.persoloan.com/adminrest/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return mRetrofit;
    }

    public ApiInterface getApi(){
        if(mRetrofit==null){
            return getClient().create(ApiInterface.class);
        }else {
            return mRetrofit.create(ApiInterface.class);
        }
    }
}
