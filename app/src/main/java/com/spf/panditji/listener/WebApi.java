package com.spf.panditji.listener;

import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.model.SignUp;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebApi {


    @POST("register.php")
    Call<SignUp> loginWithPhone(@Body RequestBody requestBody);

    @POST("verify_otp.php")
    Call<OtpResponse> sendingOtp(@Body RequestBody requestBody);
}
