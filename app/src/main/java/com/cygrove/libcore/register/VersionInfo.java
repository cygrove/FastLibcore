package com.cygrove.libcore.register;

import java.io.Serializable;

public class VersionInfo implements Serializable {
    /**
     * version : 3.05.00
     * showVer : 3.5.0
     * title : 兴创巢3.5.0版本更新
     * url : https://itunes.apple.com/cn/app/xing-shou-fu-zhuan-wei-shi/id1140424356?mt=8
     * upContent : 3.5提审版
     * upType : 0
     * upAudit : 1
     * upStatus : 1
     * upDate : null
     * versionCode : 30500
     */

    private String version;
    private String showVer;
    private String title;
    private String url;
    private String upContent;
    private int upType;
    private int upAudit;
    private int upStatus;
    private Object upDate;
    private int versionCode;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getShowVer() {
        return showVer;
    }

    public void setShowVer(String showVer) {
        this.showVer = showVer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpContent() {
        return upContent;
    }

    public void setUpContent(String upContent) {
        this.upContent = upContent;
    }

    public int getUpType() {
        return upType;
    }

    public void setUpType(int upType) {
        this.upType = upType;
    }

    public int getUpAudit() {
        return upAudit;
    }

    public void setUpAudit(int upAudit) {
        this.upAudit = upAudit;
    }

    public int getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }

    public Object getUpDate() {
        return upDate;
    }

    public void setUpDate(Object upDate) {
        this.upDate = upDate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}