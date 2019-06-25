package com.xiaolin.blog.controller;

import com.xiaolin.blog.model.Comment;
import com.xiaolin.blog.model.User;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",this.commentService.getComments(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        User user = (User)session.getAttribute("user");

        if(user==null)comment.setAdmin(false);
        else{
            comment.setAdmin(true);
            comment.setAvatar(user.getAvatar());
        }
        this.commentService.postComment(comment);
        // do remember that there should not be space after space
        return "redirect:/comments/"+ blogId;
    }
}
