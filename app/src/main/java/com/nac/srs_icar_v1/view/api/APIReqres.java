package com.nac.srs_icar_v1.view.api;


import com.nac.sampleapp.view.api.model.AccountEntity;
import com.nac.sampleapp.view.api.model.TokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIReqres {
    @POST("login")
    @Headers("Content-Type:application/json")
    Call<TokenModel> login(@Body AccountEntity body);
}
