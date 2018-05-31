package my.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers("/welcome").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')").and()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/welcome").failureForwardUrl("/login?error").and().logout().logoutSuccessUrl("/login?logout");
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
