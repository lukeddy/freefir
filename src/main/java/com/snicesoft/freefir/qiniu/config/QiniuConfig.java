package com.snicesoft.freefir.qiniu.config;

public class QiniuConfig {
    private String accessKey;
    private String secretKey;
    /**
     * bucket 空间名
     */
    private String bucket;
    /**
     * 访问地址
     */
    private String baseUrl;
    /**
     * 文件保存的文件夹
     */
    private String prefix;
    /**
     * 文件临时保存路径
     */
    private String tempPath;
    /**
     * 是否是私有空间
     */
    private boolean isPrivate;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
