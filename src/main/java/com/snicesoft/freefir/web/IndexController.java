package com.snicesoft.freefir.web;

import com.snicesoft.freefir.base.Result;
import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import com.snicesoft.freefir.qiniu.service.QiniuService;
import com.snicesoft.freefir.response.ParserResponse;
import com.snicesoft.freefir.response.PreviewResponse;
import com.snicesoft.freefir.service.ProjectService;
import com.snicesoft.freefir.service.QRService;
import com.snicesoft.freefir.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    ProjectService projectService;
    @Autowired
    VersionService versionService;
    @Autowired
    QRService qrService;
    @Autowired
    QiniuService qiniuService;

    @GetMapping({"/", ""})
    public String index(Model model) {
        model.addAttribute("ps", projectService.getProjects());
        return "index";
    }

    /**
     * 预览
     *
     * @param shortUrl
     * @param model
     * @return
     */
    @GetMapping("/{shortUrl}")
    public String index(@PathVariable String shortUrl, Model model) {
        Project p = projectService.findByShortUrl(shortUrl);
        if (p != null) {
            Version v = versionService.findTopByProjectOrderByAddDateDesc(p);
            model.addAttribute("p", new PreviewResponse(p, v));
            return "preview";
        }
        return "redirect:/error";
    }

    @PostMapping("/uploadpk")
    @ResponseBody
    public Result uploadPk(@RequestParam("file") MultipartFile file) {
        ParserResponse response = projectService.parseApk(file);
        if (response != null)
            return Result.ok(response);
        return Result.fail("上传失败");
    }

    @GetMapping("/download/{shortUrl}")
    public String download(@PathVariable String shortUrl) {
        Project p = projectService.findByShortUrl(shortUrl);
        if (p != null) {
            Version v = versionService.findTopByProjectOrderByAddDateDesc(p);
            String downloadUrl = v.getDownloadUrl();
            if (qiniuService.getConfig().isPrivate()) {
                downloadUrl = qiniuService.getTokenUrl(downloadUrl);
            }
            return String.format("redirect:%s", downloadUrl);
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/qr/{shortUrl}")
    public void qr(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) {
        try {
            String content = String.format("%sdownload/%s", request.getAttribute("basePath"), shortUrl);
            qrService.encode(content, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
