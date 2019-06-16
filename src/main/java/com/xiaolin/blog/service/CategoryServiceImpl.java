package com.xiaolin.blog.service;

import com.xiaolin.blog.Exception.NotFoundException;
import com.xiaolin.blog.dao.CategoryRepository;
import com.xiaolin.blog.model.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Type saveCategory(Type type) {
        return this.categoryRepository.save(type);
    }

    @Transactional
    @Override
    public Type getCategory(Long id) {
        return this.categoryRepository.getOne(id);
    }

    @Override
    public Type getCategoryByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type res =  this.categoryRepository.getOne(id);
        if(res==null) throw new NotFoundException("Category not exists");
        // otherwise copy and update
        BeanUtils.copyProperties(type, res);
        return this.categoryRepository.save(res);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
