package com.xiaolin.blog.controller;

import com.xiaolin.blog.Exception.NotFoundException;
import com.xiaolin.blog.model.BlogQuery;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.CategoryService;
import com.xiaolin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public String index(@PageableDefault(size=5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("categories", categoryService.getTopCategories(5));
        model.addAttribute("tags", tagService.getTop(6));
        model.addAttribute("recommends", blogService.listTopRecommendBlogs(8));
        return "index";
    }

    @RequestMapping("/categories")
    public String categories() {
        return "types";
    }

    @RequestMapping("/tags")
    public String tags() {
        return "tags";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/archives")
    public String archives() {
        return "archives";
    }

    @RequestMapping("/blog")
    public String blog() {
        return "blog";
    }



}
