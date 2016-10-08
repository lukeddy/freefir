package com.snicesoft.freefir.service;

import java.util.List;

import com.snicesoft.freefir.request.ProjectRequest;
import com.snicesoft.freefir.response.ParserResponse;
import org.springframework.web.multipart.MultipartFile;

import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.response.ProjectResponse;

public interface ProjectService {
    ParserResponse parseApk(MultipartFile file);

    Project save(ProjectRequest request);

    Project findByPkName(String pkName);

    Project findByShortUrl(String shortUrl);

    Project findOne(Long id);

    List<ProjectResponse> getProjects();
}
