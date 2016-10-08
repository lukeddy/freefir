package com.snicesoft.freefir.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.snicesoft.freefir.base.BaseEntity;

@Entity
@Table(name = "free_project")
public class Project extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    @Lob
    @Column(columnDefinition = "BLOB")
    private String icon;
    private String shortUrl;
    private String pkName;
    private String versionName;
    private long versionCode;
    private String appDesc;

    public Project() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Project(Long id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
}
