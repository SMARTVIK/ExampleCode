package com.spf.panditji.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spf.panditji.R;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText email = findViewById(R.id.email_edit_text);
        final EditText mobile = findViewById(R.id.mobile_edit_text);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(email.getText().toString().isEmpty()){
                    email.setError("Can't be empty!!");
                    email.requestFocus();
                    return;
                }*/

                if(mobile.getText().toString().isEmpty()){
                    mobile.setError("Can't be empty!!");
                    mobile.requestFocus();
                    return;
                }

                if(!Utility.checkInternetConnection(ForgotPasswordActivity.this)){
                    Toast.makeText(ForgotPasswordActivity.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog();

                ApiUtil.getInstance().forgotPassword(mobile.getText().toString(), new Callback<ForgotPasswordModel>() {

                    @Override
                    public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                        if(response.code() == 200 && response.body().getError() == 0){
                            startActivity(new Intent(ForgotPasswordActivity.this, VerifyEmailActivity.class)
                                    .putExtra(Constants.MOBILE, mobile.getText().toString())
                                    .putExtra(Constants.CREATE_NEW_PASS, "true"));
                        }else{
                            Toast.makeText(ForgotPasswordActivity.this, ""+response.body().getAlert(), Toast.LENGTH_SHORT).show();
                            hideLoader();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                        hideLoader();
                    }
                });

            }
        });
    }


    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = ProgressDialog.show(this,"","Please wait..",false);
        }else{
            progressDialog.show();
        }
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
