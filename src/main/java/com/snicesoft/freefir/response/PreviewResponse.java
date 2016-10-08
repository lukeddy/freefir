package com.snicesoft.freefir.response;

import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.utils.ByteUtils;

import java.util.Date;

/**
 * Created by zhe on 2016/10/5.
 */
public class PreviewResponse {
    String icon;
    String qrUrl;
    String appName;
    String buildName;
    String appSize;
    Date date;
    String downloadUrl;

    public PreviewResponse() {
    }

    public PreviewResponse(Project p, Version v) {
        setIcon(p.getIcon());
        setQrUrl(String.format("/qr/%s", p.getShortUrl()));
        setAppName(p.getName());
        setBuildName(String.format("%s(Build %d)", v.getVersionName(), v.getVersionCode()));
        setDownloadUrl(String.format("/download/%s", p.getShortUrl()));
        setDate(v.getAddDate());
        setAppSize(ByteUtils.bytes2kb(v.getAppSize()));
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
