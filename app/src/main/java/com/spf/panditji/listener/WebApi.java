package com.spf.panditji.listener;

import com.spf.panditji.model.AddressModel;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.BookingListModel;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.OrderModel;
import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.model.PagerModel;
import com.spf.panditji.model.PanditDetailsModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.SignUp;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.view.AddressResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebApi {


    @POST("register_user.php")
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

    @POST("pooja_details")
    Call<List<PujaDetailModel>> getPujaDetails(@Body RequestBody requestBody);

    @POST("pandit_profile")
    Call<PanditDetailsModel> getPanditDetails(@Body RequestBody requestBody);

    @POST("check_availability")
    Call<AvailabilityModel> getPandit(@Body RequestBody requestBody);

    @POST("order")
    Call<List<OrderModel>> order(@Body RequestBody requestBody);

    @POST("address")
    Call<List<AddressModel>> getAllAddresses(@Body RequestBody requestBody);

    @POST("address_add")
    Call<AddressResponse> addAddress(@Body RequestBody requestBody);

    @POST("home_cat")
    Call<List<PagerModel>> getHomeCat();

    @POST("booking_history")
    Call<List<BookingListModel>> getBookings(@Body RequestBody userId);
}
