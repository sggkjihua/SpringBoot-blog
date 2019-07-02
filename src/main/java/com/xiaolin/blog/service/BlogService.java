package com.xiaolin.blog.service;


import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.model.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog saveBlog(Blog blog);
    Blog getBlog(Long id);
    Blog getBlogAsHTML(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Pageable pageable, Long tagId);
    Map<String, List<Blog>> listBlogByYear();
    Page<Blog> listSearchResults(String query, Pageable pageable);
    List<Blog> listTopRecommendBlogs(int size);
    Long cntBlogs();
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
}
