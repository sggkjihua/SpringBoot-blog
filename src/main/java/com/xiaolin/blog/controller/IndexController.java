package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.Blog;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;

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

    @PostMapping("/search")
    public String search(@PageableDefault(size=5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @RequestParam String query){
        model.addAttribute("page", blogService.listSearchResults("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }


    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/archives")
    public String archives() {
        return "archives";
    }

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogAsHTML(id);
        model.addAttribute("blog", blog);
        return "blog";
    }


    @RequestMapping("/*")
    public String random(){
        return "index";
    }

}
