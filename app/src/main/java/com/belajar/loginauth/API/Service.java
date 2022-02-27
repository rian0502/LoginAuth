package com.belajar.loginauth.API;

import com.belajar.loginauth.Models.LoginReq;
import com.belajar.loginauth.Models.LoginRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Service {
    @POST("read.php")
    Call<LoginRes> userLogin(@Body LoginReq loginReq);
}
