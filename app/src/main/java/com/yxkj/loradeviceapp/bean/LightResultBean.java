package com.yxkj.loradeviceapp.bean;

import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class LightResultBean extends BaseBean {
    @SerializedName("causeList")
    public Object causeList;
    @SerializedName("data")
    public DataBean data;
    @SerializedName("msg")
    public String msg;
    @SerializedName("qrCodeStr")
    public Object qrCodeStr;
    @SerializedName("visitingRecord")
    public Object visitingRecord;
    @SerializedName("visitingRecordService")
    public Object visitingRecordService;

    public static LightResultBean objectFromData(String str) {
        return (LightResultBean) new Gson().fromJson(str, LightResultBean.class);
    }

    public static LightResultBean objectFromData(String str, String key) {
        try {
            return (LightResultBean) new Gson().fromJson(new JSONObject(str).getString(str), LightResultBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<LightResultBean> arrayLightResultBeanFromData(String str) {
        return (List) new Gson().fromJson(str, new TypeToken<ArrayList<LightResultBean>>() {
            /* class com.example.loradevicesapp.bean.LightResultBean.AnonymousClass1 */
        }.getType());
    }

    public static List<LightResultBean> arrayLightResultBeanFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return (List) new Gson().fromJson(jsonObject.getString(str), new TypeToken<ArrayList<LightResultBean>>() {
                /* class com.example.loradevicesapp.bean.LightResultBean.AnonymousClass2 */
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }
}