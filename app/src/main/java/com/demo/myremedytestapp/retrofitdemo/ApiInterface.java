package com.demo.myremedytestapp.retrofitdemo;

import com.google.gson.JsonArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("employee/{id}")
    Call<Employee> getEmployeeDetails(@Path("id") String empId);

    @FormUrlEncoded
    @POST("contactus_api.php")
    Call<JsonArray> sendContactDetails(@Field("name") String userName,
                                       @Field("email") String userEmail,
                                       @Field("phone") String userMobile,
                                       @Field("comments") String userComments);

}
