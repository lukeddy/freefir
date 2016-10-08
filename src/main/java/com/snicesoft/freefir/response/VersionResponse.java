package com.snicesoft.freefir.response;

import com.snicesoft.freefir.domain.Version;

import java.util.Date;

/**
 * Created by zhe on 2016/10/4.
 */
public class VersionResponse {
    String buildName;
    String ulogs;
    String downloadUrl;
    Date date;

    public VersionResponse() {
    }

    public VersionResponse(Version v) {
        setBuildName(String.format("%s(Build %d)", v.getVersionName(), v.getVersionCode()));
        setDownloadUrl(v.getDownloadUrl());
        setDate(v.getAddDate());
        setUlogs(v.getUpdateContent());
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getUlogs() {
        return ulogs;
    }

    public void setUlogs(String ulogs) {
        this.ulogs = ulogs;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
