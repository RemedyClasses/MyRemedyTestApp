package com.demo.myremedytestapp.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.myremedytestapp.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetofitTestActivity extends AppCompatActivity {
    private Button btnCallRetrofit;
    private TextView textResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retofit_test);
        btnCallRetrofit  = findViewById(R.id.btnCallRetrofit);
        textResponse  = findViewById(R.id.textResponse);

        btnCallRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Perform action here
           //     getEmployeeDetails("72186");

                String name = "Remedy Class";
                String email = "remedy@gmail.com";
                String phone = "636738983";
                String comments = "This is comment from Remedy classes.";
                //Call methods from here
                sendComments(name, email, phone, comments);
            }

        });


        String serverResponse = "{\n" +
                "  \"org_name\": \"Remedy classes\",\n" +
                "  \"start_date\": \"15th May, 2015\",\n" +
                "  \"stud_list\": [\n" +
                "    {\n" +
                "      \"stud_name\": \"Surjeet\",\n" +
                "      \"stud_address\": \"Kolkata\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stud_name\": \"Ajeet\",\n" +
                "      \"stud_address\": \"Ramgarh\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stud_name\": \"Hasan\",\n" +
                "      \"stud_address\": \"Kolkata\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stud_name\": \"Indrajeet\",\n" +
                "      \"stud_address\": \"Patna\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stud_name\": \"Anisha\",\n" +
                "      \"stud_address\": \"Kolkata\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        Log.i("@@response = ", "\n"+serverResponse);

        try {
            //For JsonArray in server-response
        //    JSONArray objMainArray = new JSONArray(serverResponse);


            //Find Address of Hasan
            //Convert Server-Response to JSONObject.
            JSONObject mainObject = new JSONObject(serverResponse);

            //Complete Student List
            JSONArray objStudentArray = mainObject.getJSONArray("stud_list");

            //Surjeet
            JSONObject student1 = objStudentArray.getJSONObject(0);
            String studName = student1.getString("stud_name");
            String studAddress = student1.getString("stud_address");

            //Hasan =====> Tarika No-1 ==> Bekar tarika
            JSONObject student2 = objStudentArray.getJSONObject(2);
            String studName2 = student2.getString("stud_name");
            String studAddress2 = student2.getString("stud_address");

            //Standered Tarika No.- 2
            for (int i = 0; i < objStudentArray.length(); i++) {
                //Get student at index position
                JSONObject student = objStudentArray.getJSONObject(i);
                //Get the name
                String myName = student.getString("stud_name");

                //Check for name equals to Hasan
                if (myName.equals("Hasan")) {
                    //Get the address
                    String myAddress = student.getString("stud_address");
                    System.out.println("Name : " + myName + " Address: " + myAddress);

                    break;
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void sendComments(String name, String email, String phone, String comments){
        //    ApiInterface apiInterface = new ApiClient().getApi();
        ApiInterface apiInterface = ApiClient.getInsance().getApi();

        Call<JsonArray> call = apiInterface.sendContactDetails(name, email, phone, comments);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    Log.d("@@ON-Success : ", String.valueOf(response.body()));
                    textResponse.setText(String.valueOf(response.body()));

                    //Json-Parsing


                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("@@Failed : ", "onFailure: "+ t.getMessage());
            }

        });
    }

    private void getEmployeeDetails(String empId) {
        ApiInterface apiInterface = ApiClient.getInsance().getApi();
        Call<Employee> call = apiInterface.getEmployeeDetails(empId);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if(response.isSuccessful())
                Log.i("@@API-Response :", response.body().toString());
                textResponse.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

        /*APIInterface apiInterface = APIClient.getInsance().getApi();
        Call<ResponseBody> call = apiInterface.updateUserProfile(userDetails.getAgent_id(),
                email, address, city, state, pin);

        call.enqueue(new Callback<ResponseBody>() {*/
    }
}
