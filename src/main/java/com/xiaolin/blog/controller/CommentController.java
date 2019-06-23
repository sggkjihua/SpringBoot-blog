package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.model.Comment;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    static String REDIRECT = "redirect: /comments/";

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;


    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        System.out.println("I seem to come here brfore!");
        model.addAttribute("comments",this.commentService.getComments(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        System.out.println(comment.toString());
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        this.commentService.postComment(comment);
        System.out.println("Did I come here???");
        return REDIRECT + comment.getId();
    }
}
