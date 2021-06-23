package com.yxkj.loradeviceapp.util

import com.orhanobut.hawk.Hawk
import com.yxkj.loradeviceapp.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.internal.Intrinsics


object RetrofitUtil  {

    fun <T> getRetrofit(api: Class<T>?): T {
        Intrinsics.checkNotNullParameter(api, "api")
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl(
            "http://" + Hawk.get(
                Constant.SERVER_IP,
                "192.168.10.1"
            ) as String as Any + ':' + Hawk.get(Constant.SERVER_PORT, 8080) as Int + '/'
        )
        builder.addConverterFactory(GsonConverterFactory.create())
        return builder.build().create(api)
    }


    fun <T> getRetrofitLoraLight(api: Class<T>?): T {
        Intrinsics.checkNotNullParameter(api, "api")
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl("http://" + "192.168.2.54" + ':' + 8081 + '/')
        builder.addConverterFactory(GsonConverterFactory.create())
        builder.build().create(api)
        return builder.build().create(api)
    }

    fun <T> sendHttp(call: Call<T>, onResponse: (T) -> Unit, onFailure:(Throwable) -> Unit) {
            call.enqueue(object:Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    response.body()?.let { onResponse(it) }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

}