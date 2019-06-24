package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.Tag;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagDisplayController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @GetMapping("tags/{id}")
    public String getTags(@PathVariable Long id, @PageableDefault(size=10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model){
        List<Tag> tags = tagService.getAll();
        if(id==-1){
            id = tags.get(0).getId();
        }
        model.addAttribute("page", blogService.listBlog(pageable, id));
        model.addAttribute("tags", tags);
        model.addAttribute("activeId", id);
        return "tags";
    }
}
