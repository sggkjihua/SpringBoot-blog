package com.xiaolin.blog.service;

import com.xiaolin.blog.Exception.NotFoundException;
import com.xiaolin.blog.dao.BlogRepository;
import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.model.BlogQuery;
import com.xiaolin.blog.model.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return this.blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return this.blogRepository.getOne(id);
    }
    @Transactional
    @Override
    public Blog getBlogByName(String name) {
        return null;
    }

    /**
     * Dynamically adding the criteria into the query
     * */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return this.blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if( blog.getTitle()!=null && !blog.getTitle().equals("")){
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getCategoryId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getCategoryId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog res =  this.blogRepository.getOne(id);
        if(res==null) throw new NotFoundException("Blog not exists");
        // otherwise copy and update
        BeanUtils.copyProperties(blog, res);
        return this.blogRepository.save(res);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        this.blogRepository.deleteById(id);
    }
}
