package com.example.demo.services;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
     }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getById(int id) {
        var u = userRepository.findById(id);
        if (u.isPresent()) return u.get();
        return null;
    }
}
