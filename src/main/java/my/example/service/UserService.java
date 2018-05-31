package my.example.service;

import my.example.entity.User;

public interface UserService {
    void save(User user);
    User findByUsername(String name);
}
