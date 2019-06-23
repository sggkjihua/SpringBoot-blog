package com.xiaolin.blog.service;
import com.xiaolin.blog.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments(Long id);
    Comment postComment(Comment comment);
}
