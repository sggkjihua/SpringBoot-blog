package com.xiaolin.blog.dao;

import com.xiaolin.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findByName(String name);
}
