package com.xiaolin.blog.service;

import com.xiaolin.blog.bcrypt.BCrypt;
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
        // User user = this.userRepository.findByEmailAndPassword(email, password);
        User user = this.userRepository.findByEmail(email);
        /**
         * validation here
         * do keep in mind that the password store in the database should be in encrypted form
         * otherwise it will throw an error
         */
        if(user!=null && BCrypt.checkpw(password, user.getPassword())) return user;
        return null;
    }
}
