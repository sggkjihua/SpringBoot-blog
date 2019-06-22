package com.xiaolin.blog.dao;

import com.xiaolin.blog.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findByName(String name);
    @Query("select t from Tag t")
    public List<Tag> getTop(Pageable pageable);


}
