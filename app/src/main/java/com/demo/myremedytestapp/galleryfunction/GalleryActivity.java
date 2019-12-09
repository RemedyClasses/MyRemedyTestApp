package com.demo.myremedytestapp.galleryfunction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.myremedytestapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.Permission;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
    private ImageView imagePreview;
    private Button btnGallery;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imagePreview = findViewById(R.id.imagePreview);
        btnGallery = findViewById(R.id.btnGallery);


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 102);
                }else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length>0){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 102);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==102){
            if(resultCode == Activity.RESULT_OK){
                //Get image uri
                imageUri = data.getData();
                //Display preview in imageview
            //    imagePreview.setImageURI(imageUri);

              /*  Picasso.get()
                        .load(imageUri)
                        .fit()
                        .into(imagePreview);*/

                try {
                    //Get image full path using its URI
                    String imageFullPath = getImagePathFromURI(imageUri);

                    //Api call to send image to server
                    uploadMultipartImage(imageFullPath, "9572498167");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getImagePathFromURI(Uri contentURI) throws Exception{
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { //checking
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void uploadMultipartImage(final String imagePath, String mobile) {
        //creating a file
        File file = new File(imagePath);
        //creating request body for file
        RequestBody user_mobile = RequestBody.create(MediaType.parse("text/plain"), mobile);

        Log.i("@@File-Name = ",mobile+"\n"+file.getName());
        //Prepare Multipart image
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part image_to_upload = MultipartBody.Part.createFormData("kyc_image",
                file.getName(), requestFile);

        //creating a call and calling the upload image method
        ApiInterface apiInterface = ApiClient.getInstance().getApi();
        Call<ResponseImageUpload> call = apiInterface.uploadImageToServer(user_mobile, image_to_upload);

        //finally performing the call
        call.enqueue(new Callback<ResponseImageUpload>() {
            @Override
            public void onResponse(Call<ResponseImageUpload> call, Response<ResponseImageUpload> response) {

                if(response.isSuccessful()){
                    Log.i("@@Response-Upload : ", response.body().getResponse().toString());

                    Picasso.get()
                            .load(response.body().getResponse().getImage_url())
                            .fit()
                            .into(imagePreview);

                }

            }

            @Override
            public void onFailure(Call<ResponseImageUpload> call, Throwable t) {
                Toast.makeText(GalleryActivity.this,
                        "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
