package com.snicesoft.freefir.web;

import com.snicesoft.freefir.base.Result;
import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.request.CheckVersionRequest;
import com.snicesoft.freefir.service.ProjectService;
import com.snicesoft.freefir.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhe on 2016/10/4.
 */
@RestController
@RequestMapping("/openapi")
public class OpenApiController {
    @Autowired
    VersionService versionService;
    @Autowired
    ProjectService projectService;

    @PostMapping("/checkVersion")
    public Result checkVersion(@RequestBody CheckVersionRequest request) {
        Project p = projectService.findByPkName(request.getPkName());
        if (p != null) {
            Version v = versionService.findByVersionCodeGreaterThan(p, request.getVersionCode());
            return Result.ok(v);
        }
        return Result.fail("app不存在");
    }
}
