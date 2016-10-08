package com.snicesoft.freefir.service;

import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.response.VersionResponse;

import java.util.List;

/**
 * Created by zhe on 2016/10/4.
 */
public interface VersionService {
    List<VersionResponse> findByProject(Project project);

    Version findTopByProjectOrderByAddDateDesc(Project project);

    Version findByVersionCodeGreaterThan(Project project, long versionCode);
}
