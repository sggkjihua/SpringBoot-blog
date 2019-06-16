package com.xiaolin.blog.service;

import com.xiaolin.blog.dao.UserRepository;
import com.xiaolin.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String email, String password) {
        User user = this.userRepository.findByEmailAndPassword(email, password);
        return user;
    }
}
