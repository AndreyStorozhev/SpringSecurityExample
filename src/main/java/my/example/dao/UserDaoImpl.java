package my.example.dao;

import my.example.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User findByUsername(String username) {
        List<User> list = getUserList();
        for (User u : list){
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    @Override
    @Transactional
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
        logger.info("User add: " + user);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> getUserList(){
        return (List<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
    }
}
