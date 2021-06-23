package com.yxkj.loradeviceapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EthernetDevInfo implements Parcelable {
    public static final Parcelable.Creator<EthernetDevInfo> CREATOR = new Parcelable.Creator<EthernetDevInfo>() {
        /* class com.example.loradevicesapp.bean.EthernetDevInfo.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public EthernetDevInfo createFromParcel(Parcel in) {
            EthernetDevInfo info = new EthernetDevInfo();
            info.setIfName(in.readString());
            info.setIpAddress(in.readString());
            info.setNetMask(in.readString());
            info.setGateWay(in.readString());
            info.setDnsAddr(in.readString());
            info.setConnectMode(in.readInt());
            info.setHwaddr(in.readString());
            return info;
        }

        @Override // android.os.Parcelable.Creator
        public EthernetDevInfo[] newArray(int size) {
            return new EthernetDevInfo[size];
        }
    };
    public static final int ETHERNET_CONN_MODE_DHCP = 1;
    public static final int ETHERNET_CONN_MODE_MANUAL = 0;
    private String dev_name = null;
    private String dns = null;
    private String gw = null;
    private String hwaddr = null;
    private String ipaddr = null;
    private int mode = 1;
    private String netmask = null;

    public void setIfName(String ifname) {
        this.dev_name = ifname;
    }

    public String getIfName() {
        return this.dev_name;
    }

    public void setIpAddress(String ip) {
        this.ipaddr = ip;
    }

    public String getIpAddress() {
        return this.ipaddr;
    }

    public void setNetMask(String ip) {
        this.netmask = ip;
    }

    public String getNetMask() {
        return this.netmask;
    }

    public void setGateWay(String gw2) {
        this.gw = gw2;
    }

    public String getGateWay() {
        return this.gw;
    }

    public void setDnsAddr(String dns2) {
        this.dns = dns2;
    }

    public String getDnsAddr() {
        return this.dns;
    }

    public void setHwaddr(String hwaddr2) {
        this.hwaddr = hwaddr2;
    }

    public String getHwaddr() {
        return this.hwaddr;
    }

    public void setConnectMode(int mode2) {
        this.mode = mode2;
    }

    public int getConnectMode() {
        return this.mode;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dev_name);
        dest.writeString(this.ipaddr);
        dest.writeString(this.netmask);
        dest.writeString(this.gw);
        dest.writeString(this.dns);
        dest.writeInt(this.mode);
        dest.writeString(this.hwaddr);
    }
}