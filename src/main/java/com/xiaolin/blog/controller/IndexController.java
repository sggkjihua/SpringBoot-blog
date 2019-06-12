package com.xiaolin.blog.controller;

import com.xiaolin.blog.Exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/{id}/{name}")
    public String index(@PathVariable int id, @PathVariable String name) {
        /*
        // 用来进行异常的检测
        String blog = null;
        if(blog==null){
            throw new NotFoundException("Blog does not exist");
        }
        */
        System.out.println(id + " 喜欢郭晓林 "+name);
        return "index";
    }

}
