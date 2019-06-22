package com.xiaolin.blog.service;


import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.model.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog saveBlog(Blog blog);
    Blog getBlog(Long id);
    Blog getBlogByName(String name);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Pageable pageable, Long tagId);
    Page<Blog> listSearchResults(String query, Pageable pageable);
    List<Blog> listTopRecommendBlogs(int size);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
}
