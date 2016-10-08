package com.snicesoft.freefir.web;

import com.snicesoft.freefir.base.Result;
import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.request.ProjectRequest;
import com.snicesoft.freefir.service.ProjectService;
import com.snicesoft.freefir.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    VersionService versionService;

    @PostMapping("/add")
    @ResponseBody
    public Result addProject(@RequestBody ProjectRequest request) {
        Project project = projectService.save(request);
        return Result.ok(project);
    }

    @GetMapping("/{id}")
    public String project(@PathVariable String id, Model model) {
        if (StringUtils.isEmpty(id))
            return "redirect:/error";
        long pid;
        try {
            pid = Long.parseLong(id);
        } catch (Exception e) {
            return "redirect:/error";
        }
        Project p = projectService.findOne(pid);
        if (p != null) {
            model.addAttribute("p", p);
            model.addAttribute("vs", versionService.findByProject(p));
            return "project/detail";
        } else {
            return "redirect:/error";
        }
    }
}
