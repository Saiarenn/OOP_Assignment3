package com.example.demo.services.interfaces;

import com.example.demo.Entity.User;

public interface IUserService {
    User createUser(User u);

    User getByEmail(String name);

    User getById(int id);
}
