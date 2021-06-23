package com.yxkj.loradeviceapp.bean;

import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherBean extends BaseBean {
    @SerializedName("causeList")
    public Object causeList;
    @SerializedName("data")
    public List<DataBean> data;
    @SerializedName(NotificationCompat.CATEGORY_MESSAGE)
    public String msg;
    @SerializedName("qrCodeStr")
    public Object qrCodeStr;
    @SerializedName("visitingRecord")
    public Object visitingRecord;
    @SerializedName("visitingRecordService")
    public Object visitingRecordService;

    public static OtherBean objectFromData(String str) {
        return (OtherBean) new Gson().fromJson(str, OtherBean.class);
    }

    public static OtherBean objectFromData(String str, String key) {
        try {
            return (OtherBean) new Gson().fromJson(new JSONObject(str).getString(str), OtherBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<OtherBean> arrayOtherBeanFromData(String str) {
        return (List) new Gson().fromJson(str, new TypeToken<ArrayList<OtherBean>>() {
            /* class com.example.loradevicesapp.bean.OtherBean.AnonymousClass1 */
        }.getType());
    }

    public static List<OtherBean> arrayOtherBeanFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return (List) new Gson().fromJson(jsonObject.getString(str), new TypeToken<ArrayList<OtherBean>>() {
                /* class com.example.loradevicesapp.bean.OtherBean.AnonymousClass2 */
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }
}