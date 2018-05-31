package my.example.dao;

import my.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    void add(User user);
    List<User> getUserList();
}
