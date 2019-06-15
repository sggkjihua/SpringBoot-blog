package com.xiaolin.blog.controller;

import com.xiaolin.blog.Exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        /*
        // 用来进行异常的检测
        String blog = null;
        if(blog==null){
            throw new NotFoundException("Blog does not exist");
        }
        */
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
