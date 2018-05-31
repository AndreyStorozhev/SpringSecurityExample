package my.example.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger logger = Logger.getLogger(SecurityServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    //ищем залогиненх пользователей
    @Override
    public String findLoggedInUsername() {
        //    Внутри SecurityContextHolder мы храним информацию о главном пользователе, взаимодействующем с этим приложением
        //    Spring Security использует объект Authentication для представления этой информации.
        //    Аутентификация будет храниться в локальном защищенном
        //    потоке SecurityContext, управляемом SecurityContextHolder
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof UserDetails)
            return ((UserDetails)details).getUsername();
        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //имя пользователя, пароль и полномочия объединяются в экземпляр класса
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        //токен передаётся для проверки
        //AuthenticationManager возвращает полностью заполненный экземпляр Authentication в случае успешной аутентификации.
        authenticationManager.authenticate(authenticationToken);

//        Устанавливается контекст безопасности путем вызова SecurityContextHolder.getContext().setAuthentication(…),
//        куда передается вернувшийся экземпляр Authentication.
//        С этого момента пользователь считается подлинным.
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            logger.info("Successfully auto logged in " + username);
        }
    }
}
