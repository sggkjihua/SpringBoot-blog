package com.xiaolin.blog.service;


import com.xiaolin.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> getAll();
    List<Tag> getTop(int size);
    List<Tag> getTags(String ids);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
}
