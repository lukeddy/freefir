package com.snicesoft.freefir.service.impl;

import com.snicesoft.freefir.base.Result;
import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.qiniu.service.QiniuService;
import com.snicesoft.freefir.repository.ProjectRepository;
import com.snicesoft.freefir.repository.VersionRepository;
import com.snicesoft.freefir.request.ProjectRequest;
import com.snicesoft.freefir.response.ParserResponse;
import com.snicesoft.freefir.response.ProjectResponse;
import com.snicesoft.freefir.service.ProjectService;
import com.snicesoft.freefir.utils.StringUtils;
import com.snicesoft.freefir.utils.ZipUtils;
import net.dongliu.apk.parser.ApkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository repository;
    @Autowired
    VersionRepository versionRepository;
    @Autowired
    QiniuService qiniuService;

    @Override
    public ParserResponse parseApk(MultipartFile file) {
        ParserResponse response = null;
        String fileName = file.getOriginalFilename();
        ApkParser apk;
        File out = new File(qiniuService.getConfig().getTempPath() + fileName);
        try {
            if (!out.getParentFile().exists())
                out.getParentFile().mkdirs();
            FileCopyUtils.copy(file.getBytes(), out);
            response = new ParserResponse();
            if (fileName.endsWith(".apk")) {
                apk = new ApkParser(out);
                Project project = findByPkName(apk.getApkMeta().getPackageName());
                String icon = "data:image/png;base64," + Base64Utils.encodeToString(ZipUtils.getBytes(out, apk.getApkMeta().getIcon()));
                response.setAppIcon(icon);
                response.setAppName(apk.getApkMeta().getLabel());
                if (project == null) {
                    response.setShortUrl(StringUtils.getRandomString(4));
                    response.setNew(true);
                } else {
                    response.setShortUrl(project.getShortUrl());
                    response.setNew(false);
                }
                response.setPkName(apk.getApkMeta().getPackageName());
                response.setVersionCode(apk.getApkMeta().getVersionCode());
                response.setVersionName(apk.getApkMeta().getVersionName());
                response.setDownloadUrl(out.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        return response;
    }

    @Override
    public Project save(ProjectRequest request) {
        Project project = findByPkName(request.getPkName());
        if (project == null) {
            project = new Project();
            project.setIcon(request.getAppIcon());
            project.setName(request.getAppName());
            project.setPkName(request.getPkName());
            project.setShortUrl(request.getShortUrl());
            project.setVersionName(request.getVersionName());
            project.setVersionCode(request.getVersionCode());
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(request.getVersionName()))
                project.setVersionCode(request.getVersionCode());
            if (!org.springframework.util.StringUtils.isEmpty(request.getVersionName()))
                project.setVersionName(request.getVersionName());
            if (!org.springframework.util.StringUtils.isEmpty(request.getAppName()))
                project.setName(request.getAppName());
            if (!org.springframework.util.StringUtils.isEmpty(request.getShortUrl()))
                project.setShortUrl(request.getShortUrl());
            if (!org.springframework.util.StringUtils.isEmpty(request.getAppDesc()))
                project.setAppDesc(request.getAppDesc());

        }
        repository.save(project);
        if (!org.springframework.util.StringUtils.isEmpty(request.getVersionName())
                && !org.springframework.util.StringUtils.isEmpty(request.getDownloadUrl())) {
            File file = new File(request.getDownloadUrl());
            String qiniuFileUrl = "";
            if (file.exists()) {
                Result<String> result = qiniuService.upFile(file);
                System.out.println(result);
                if (result.getStatus() == 200) {
                    qiniuFileUrl = result.getData();
                    file.delete();
                }
            } else {
                return project;
            }
            if (org.springframework.util.StringUtils.isEmpty(qiniuFileUrl)) {
                return project;
            }
            Version version = new Version();
            version.setAddDate(new Date());
            version.setProject(project);
            version.setUpdateContent(request.getUlogs());
            version.setVersionCode(request.getVersionCode());
            version.setVersionName(request.getVersionName());
            version.setAppSize(request.getFileSize());

            version.setDownloadUrl(qiniuFileUrl);
            versionRepository.save(version);
        }
        return project;
    }

    @Override
    public Project findByPkName(String pkName) {
        return repository.findTopByPkName(pkName);
    }

    @Override
    public Project findByShortUrl(String shortUrl) {
        return repository.findTopByShortUrl(shortUrl);
    }

    @Override
    public Project findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ProjectResponse> getProjects() {
        List<ProjectResponse> response = new ArrayList<ProjectResponse>();
        List<Project> list = (List<Project>) repository.findAll();
        if (!ListUtils.isEmpty(list)) {
            for (Project p : list) {
                ProjectResponse ps = new ProjectResponse();
                ps.setId(p.getId());
                ps.setAppIcon(p.getIcon());
                ps.setAppName(p.getName());
                ps.setPkName(p.getPkName());
                ps.setShortUrl(p.getShortUrl());
                Version v = versionRepository.findTopByProjectOrderByAddDateDesc(p);
                if (v != null) {
                    ps.setVersionCode(v.getVersionCode());
                    ps.setVersionName(v.getVersionName());
                    ps.setBuildName(String.format("%s(Build %d)", v.getVersionName(), v.getVersionCode()));
                }
                response.add(ps);
            }
        }
        return response;
    }
}
