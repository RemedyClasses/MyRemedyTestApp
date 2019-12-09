package com.demo.myremedytestapp.galleryfunction;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Multipart
    @POST("upload_image")
    Call<ResponseImageUpload> uploadImageToServer(@Part("mobile") RequestBody userMobile,
                                                  @Part MultipartBody.Part panImage);
}
