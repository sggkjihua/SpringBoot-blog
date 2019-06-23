package com.xiaolin.blog.service;
import com.xiaolin.blog.dao.CommentRepository;
import com.xiaolin.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Long id) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return this.commentRepository.findByBlogId(id, sort);
    }

    @Transactional
    @Override
    public Comment postComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(this.commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreatedTime(new Date());
        return commentRepository.save(comment);
    }
}
