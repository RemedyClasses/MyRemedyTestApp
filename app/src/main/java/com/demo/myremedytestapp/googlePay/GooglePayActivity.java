package com.demo.myremedytestapp.googlePay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.myremedytestapp.R;

import java.util.ArrayList;

public class GooglePayActivity extends AppCompatActivity {
    private static final String TAG = "@@GooglePayActivity";
    private Context mContext = this;
    private Button btnPayNow;
    private EditText editUpiId, editName, editAmount, editNotes;
    private final int UPI_PAYMENT_CODE = 111;
    private final String GOOGLE_PAY_UPI_URL = "com.google.android.apps.nbu.paisa.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_pay);

        btnPayNow = findViewById(R.id.btnPayNow);
        editUpiId = findViewById(R.id.editUpiId);
        editName = findViewById(R.id.editName);
        editAmount = findViewById(R.id.editAmount);
        editNotes = findViewById(R.id.editNotes);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upiID = editUpiId.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String amount = editAmount.getText().toString().trim();
                String notes = editNotes.getText().toString().trim();

                if (upiID.equalsIgnoreCase("")){
                    Toast.makeText(mContext, "UPI id is required", Toast.LENGTH_SHORT).show();
                }else if (name.equalsIgnoreCase("")){
                    Toast.makeText(mContext, "name id is required", Toast.LENGTH_SHORT).show();
                }else if (amount.equalsIgnoreCase("")){
                    Toast.makeText(mContext, "amount id is required", Toast.LENGTH_SHORT).show();
                }else if (notes.equalsIgnoreCase("")){
                    Toast.makeText(mContext, "notes is required", Toast.LENGTH_SHORT).show();
                }else {
                    payUsingGooglePay(name, upiID, amount, notes);
                }
            }
        });

    }

    private void payUsingGooglePay(String name, String upiID, String amount, String notes) {
        Log.i(TAG, "payUsingGooglePay: "+name+", "+upiID+", "+amount+", "+notes);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiID)
                .appendQueryParameter("pn", name)
                /*.appendQueryParameter("mc", upiID)
                .appendQueryParameter("tid", upiID)
                .appendQueryParameter("tr", upiID)*/
                .appendQueryParameter("tn", notes)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
            //    .appendQueryParameter("refUrl", "www.remedyclasses.in")
                .build();

        Intent gPayIntent = new Intent(Intent.ACTION_VIEW);
        gPayIntent.setData(uri);
        /*intent.setPackage(GOOGLE_PAY_UPI_URL);
        startActivityForResult(intent, UPI_PAYMENT_CODE);*/

        Intent chooser = Intent.createChooser(gPayIntent, "Pay With");
        if(null!= chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser, UPI_PAYMENT_CODE);
        }else {
            Toast.makeText(mContext, "No UPI app is available. Please install one to continue.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+ requestCode);

        switch (requestCode){
            case UPI_PAYMENT_CODE:
                if(resultCode==Activity.RESULT_OK){
                    if(data!=null) {
                        String trnxData = data.getStringExtra("response");
                        Log.i(TAG, "onActivityResult: " + trnxData);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trnxData);
                        upiPaymentDataOperations(dataList);

                    /*
                        txnId=PTMa06946dec3bb41d79bf952163dac796a
                        responseCode=U30-XH
                        ApprovalRefNo=934243420531
                        Status=FAILURE
                        =============================================
                        txnId=PTMa06946dec3bb41d79bf952163dac796a
                        responseCode=U30-XH
                        ApprovalRefNo=934243420531
                        Status=FAILURE
                    */

                    }else {
                        Log.i(TAG, "onActivityResult: "+ "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("Nothing");
                        upiPaymentDataOperations(dataList);
                    }

                }else if(resultCode==Activity.RESULT_CANCELED){
                    Log.i(TAG, "onActivityResult: "+ "Payment Cancelled");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("Cancelled");
                    upiPaymentDataOperations(dataList);
                }else {
                    Log.i(TAG, "onActivityResult: "+ "Something went wrong");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("Nothing");
                    upiPaymentDataOperations(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperations(ArrayList<String> dataList) {
        for(String dataString : dataList){
            Log.i(TAG, "upiPaymentDataOperations: "+dataString);
        }
    }
}
