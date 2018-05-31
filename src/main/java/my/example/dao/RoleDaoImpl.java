package my.example.dao;

import my.example.entity.Role;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final static Logger logger = Logger.getLogger(RoleDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Role findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role role = session.load(Role.class, id);
        logger.info("Class load: " + role);
        return role;
    }
}
