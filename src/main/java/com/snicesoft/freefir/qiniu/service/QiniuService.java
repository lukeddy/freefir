package com.snicesoft.freefir.qiniu.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import com.snicesoft.freefir.base.Result;
import com.snicesoft.freefir.qiniu.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by zhe on 2016/10/7.
 */
@Service
public class QiniuService {
    @Autowired
    QiniuConfig config;

    public QiniuConfig getConfig() {
        return config;
    }

    public Result<String> upFile(File file) {
        Result<String> result = new Result<String>();
        if (config == null || StringUtils.isNullOrEmpty(config.getAccessKey())
                || StringUtils.isNullOrEmpty(config.getSecretKey()) || StringUtils.isNullOrEmpty(config.getBucket())) {
            return new Result<String>(Response.InvalidArgument, "InvalidArgument", "");
        }
        String key = config.getPrefix() + File.separator + file.getName();
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        String uploadToken = auth.uploadToken(config.getBucket());
        UploadManager uploadManager = new UploadManager();
        try {
            Response response = uploadManager.put(file, key, uploadToken);
            result.setStatus(response.statusCode);
            result.setMsg("上传成功");
            if (config.getBaseUrl().endsWith("/")) {
                result.setData(config.getBaseUrl() + key);
            } else {
                result.setData(config.getBaseUrl() + "/" + key);
            }

        } catch (QiniuException e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setMsg("上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setMsg("上传失败");
        }
        return result;
    }

    public String getTokenUrl(String url) {
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        return auth.privateDownloadUrl(url, 3600);
    }
}
