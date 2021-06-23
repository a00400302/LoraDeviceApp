package com.yxkj.loradeviceapp.bean;

import java.io.Serializable;
import java.util.List;

public class ModelsBean implements Serializable {
    public String AgentId;
    public String AgentName;
    public String Location = "默认";
    public int devicetype;
    public Boolean isOnline;
    public int modelId;
    public String name;
    public Boolean status;
    public List<TimeswitchBean> timeswitch;
    public int values1;
    public int values2;
    public int values3;
}