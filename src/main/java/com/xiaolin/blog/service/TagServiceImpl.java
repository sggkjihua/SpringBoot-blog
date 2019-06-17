package com.xiaolin.blog.service;

import com.xiaolin.blog.Exception.NotFoundException;
import com.xiaolin.blog.dao.TagRepository;
import com.xiaolin.blog.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;


    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return this.tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return this.tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return this.tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return this.tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag res =  this.tagRepository.getOne(id);
        if(res==null) throw new NotFoundException("Tag not exists");
        // otherwise copy and update
        BeanUtils.copyProperties(tag, res);
        return this.tagRepository.save(res);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        this.tagRepository.deleteById(id);
    }
}
