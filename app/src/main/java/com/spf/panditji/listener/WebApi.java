package com.spf.panditji.listener;

import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.SignUp;
import com.spf.panditji.model.UserProfileModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebApi {


    @POST("register.php")
    Call<SignUp> loginWithPhone(@Body RequestBody requestBody);

    @POST("verify_otp.php")
    Call<OtpResponse> sendingOtp(@Body RequestBody requestBody);

    @POST("main_cat")
    Call<List<CategoryModel>> getCategories();

    @POST("pandit")
    Call<List<PopularPanditModel>> getPopularPandit();

    @POST("home_cat")
    Call<List<PopularPoojaModel>> getPopularPoojaList();

    @POST("cat")
    Call<List<PujaModel>> getListPujas(@Body RequestBody requestBody);

    @POST("user_profile")
    Call<List<UserProfileModel>> getUserProfile(@Body RequestBody requestBody);

    @POST("login")
    Call<SignInResponse> signIn(@Body RequestBody requestBody);
}
