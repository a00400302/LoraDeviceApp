package com.yxkj.loradeviceapp.bean;

import java.io.Serializable;
import java.util.List;

public class AgentBean extends BaseBean implements Serializable {
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        public String agentId;
        public Boolean isOnline;
        public List<ModelsBean> models;
        public String name;
    }
}