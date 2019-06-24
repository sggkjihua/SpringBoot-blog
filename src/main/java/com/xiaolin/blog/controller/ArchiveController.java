package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String ,List<Blog>> map =  this.blogService.listBlogByYear();
        Long total = this.blogService.cntBlogs();
        model.addAttribute("map", map);
        model.addAttribute("total", total);
        return "archives";
    }
}
