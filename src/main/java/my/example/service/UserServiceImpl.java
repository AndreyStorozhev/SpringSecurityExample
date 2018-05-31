package my.example.service;

import my.example.dao.RoleDao;
import my.example.dao.UserDao;
import my.example.entity.Role;
import my.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(User user) {
        //сетаем пароль пользователя который пришёл с формы
        user.setPassword(user.getPassword());
        Set<Role> roles = new HashSet<>();
        //записываем роль обычного юзера по умолчанию
        roles.add(roleDao.findById(1L));
        user.setRoles(roles);
        userDao.add(user);
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }
}
