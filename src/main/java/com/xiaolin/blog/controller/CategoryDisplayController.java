package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.BlogQuery;
import com.xiaolin.blog.model.Type;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryDisplayController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("categories/{id}")
    public String getCategory(@PathVariable Long id, @PageableDefault(size=5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model){
        List<Type> categories = categoryService.getAllCategories();
        if(id==-1){
            id = categories.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setCategoryId(id);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("categories", categories);
        model.addAttribute("activeId", id);
        return "types";
    }
}
