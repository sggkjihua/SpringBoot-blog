package com.xiaolin.blog.dao;

import com.xiaolin.blog.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
