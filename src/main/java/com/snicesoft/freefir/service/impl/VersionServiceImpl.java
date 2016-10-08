package com.snicesoft.freefir.service.impl;

import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.repository.VersionRepository;
import com.snicesoft.freefir.response.VersionResponse;
import com.snicesoft.freefir.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhe on 2016/10/4.
 */
@Service("versionService")
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionRepository versionRepository;

    @Override
    public List<VersionResponse> findByProject(Project p) {
        List<VersionResponse> body = null;
        List<Version> versions = (List<Version>) versionRepository.findByProjectOrderByAddDateDesc(p);
        if (!ListUtils.isEmpty(versions)) {
            body = new ArrayList<VersionResponse>();
            for (Version v : versions) {
                body.add(new VersionResponse(v));
            }
        }
        return body;
    }

    @Override
    public Version findTopByProjectOrderByAddDateDesc(Project project) {
        return versionRepository.findTopByProjectOrderByAddDateDesc(project);
    }

    @Override
    public Version findByVersionCodeGreaterThan(Project project, long versionCode) {
        return versionRepository.findTopByProjectAndVersionCodeGreaterThanOrderByAddDateDesc(project, versionCode);
    }
}
