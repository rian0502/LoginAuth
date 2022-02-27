package com.belajar.loginauth.API;

import com.belajar.loginauth.Models.LoginReq;
import com.belajar.loginauth.Models.LoginRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginRes> userLogin(@Field("username") String username,
                             @Field("password") String password);
}
