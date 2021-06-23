package com.yxkj.loradeviceapp.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;


public class DataBean implements Serializable {
    @SerializedName("areaId")
    public Integer areaId;
    @SerializedName("areaName")
    public Object areaName;
    @SerializedName("ctrName")
    public String ctrName;
    @SerializedName("id")
    public Integer id;
    @SerializedName("isOpen")
    public Integer isOpen;
    @SerializedName("isWork")
    public Integer isWork;
    @SerializedName("light")
    public Integer light;
    @SerializedName("luminance")
    public Integer luminance;
    @SerializedName("serialNum")
    public String serialNum;
    @SerializedName("type")
    public Integer type;

    public static DataBean objectFromData(String str) {
        return (DataBean) new Gson().fromJson(str, DataBean.class);
    }

    public static DataBean objectFromData(String str, String key) {
        try {
            return (DataBean) new Gson().fromJson(new JSONObject(str).getString(str), DataBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List arrayDataBeanFromData(String str) {
        return new Gson().fromJson(str, new TypeToken<ArrayList<DataBean>>() {
            /* class com.example.loradevicesapp.bean.DataBean.AnonymousClass1 */
        }.getType());
    }

    public static List arrayDataBeanFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), new TypeToken<ArrayList<DataBean>>() {
                /* class com.example.loradevicesapp.bean.DataBean.AnonymousClass2 */
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }
}