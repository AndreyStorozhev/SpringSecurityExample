package my.example.service;

import my.example.dao.UserDao;
import my.example.entity.Role;
import my.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //получаем пользователя
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Unknown account");
        //сет разрешений
        //GrantedAuthority Представляет полномочия, предоставленные объекту аутентификации.
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //добавлем в разрешения для данного пользоваетля все роли которые у него хранятся в бд
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // на основании полученныйх даных формируем объект UserDetails
        // который позволит проверить введеный пользователем логин и пароль
        // и уже потом аутентифицировать пользователя
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
