package my.example.config;

import my.example.entity.Role;
import my.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = {"my.example.service", "my.example.dao", "my.example.validator"})
public class SpringConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("sql.Url"));
        dataSource.setUsername(environment.getProperty("sql.user"));
        dataSource.setPassword(environment.getProperty("sql.password"));
        dataSource.setDriverClassName(environment.getProperty("sql.connection.driver"));
        return dataSource;
    }
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties props = new Properties();
        props.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(User.class, Role.class);
        return factoryBean;
    }
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
