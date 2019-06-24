package com.xiaolin.blog.service;
import com.xiaolin.blog.dao.CommentRepository;
import com.xiaolin.blog.model.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Long id) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Comment> comments = this.commentRepository.findByBlogIdAndParentCommentNull(id, sort);
        return topComments(comments);
    }

    private List<Comment> topComments(List<Comment> pars){
        List<Comment> sub = new ArrayList<>();
        for(Comment comment: pars){
            if(comment.getParentComment()!=null) continue;
            Comment copy = new Comment();
            BeanUtils.copyProperties(comment, copy);
            sub.add(copy);
        }
        combineChildren(sub);
        return sub;
    }

    private List<Comment> childs;

    private void combineChildren(List<Comment> pars){
        for(Comment p: pars){
            childs = new ArrayList<>();
            List<Comment> subs = p.getReplyComments();
            for(Comment child: subs){
                dfs(child);
            }
            p.setReplyComments(childs);
        }
        return;
    }

    private void dfs(Comment cur){
        childs.add(cur);
        for(Comment child: cur.getReplyComments()){
            dfs(child);
        }
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
