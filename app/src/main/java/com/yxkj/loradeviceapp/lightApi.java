package com.yxkj.loradeviceapp;


import com.yxkj.loradeviceapp.bean.AgentBean;
import com.yxkj.loradeviceapp.bean.BaseBean;
import com.yxkj.loradeviceapp.bean.LightResultBean;
import com.yxkj.loradeviceapp.bean.OtherBean;

import java.util.HashMap;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface lightApi {
    @FormUrlEncoded
    @POST("light/agents/add")
    Call<BaseBean> addAgents(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("light/model/add")
    Call<BaseBean> addModel(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("light/time/add")
    Call<BaseBean> addTime(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("light/agents/delete")
    Call<BaseBean> deleteAgents(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("light/model/delete")
    Call<BaseBean> deleteModel(@FieldMap HashMap<String, Object> hashMap);

    @FormUrlEncoded
    @POST("light/time/delete")
    Call<BaseBean> deleteTime(@FieldMap HashMap<String, Object> hashMap);

    @GET("light/agents")
    Call<AgentBean> getAgents();

    @GET("/access/sys/lightList")
    Call<OtherBean> getLighList();

    @GET("light/control/")
    Call<BaseBean> ledControl(@Query("modelId") int i, @Query("status") boolean z);

    @POST("/access/sys/setLightParams")
    Call<LightResultBean> setParams(@Body RequestBody requestBody);
}