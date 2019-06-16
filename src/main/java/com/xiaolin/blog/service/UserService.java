package com.xiaolin.blog.service;

import com.xiaolin.blog.model.User;

public interface UserService {
    User checkUser(String email, String password);
}
