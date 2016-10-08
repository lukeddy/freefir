package com.snicesoft.freefir.request;

/**
 * Created by zhe on 2016/10/4.
 */
public class CheckVersionRequest {
    long versionCode;
    String pkName;

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }
}
