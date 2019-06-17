package com.xiaolin.blog.service;

import com.xiaolin.blog.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Type saveCategory(Type type);
    Type getCategory(Long id);
    Type getCategoryByName(String name);
    Page<Type> listType(Pageable pageable);
    Type updateType(Long id, Type type);
    void deleteType(Long id);
    List<Type> getAllCategories();
}
