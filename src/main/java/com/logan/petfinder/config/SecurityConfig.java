package com.logan.petfinder.config;

import com.logan.petfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String usersQuery = "SELECT username, password, active FROM users where username = ?";
        String rolesQuery =" SELECT u.username, r.name FROM roles r INNER JOIN users u ON u.role_id = r.id WHERE u.username = ?";
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home", "/find/**", "/net/**", "/user/**").hasRole("USER")
                .antMatchers("/signup", "/login", "/").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    private AuthenticationSuccessHandler successHandler(){
        return (request, response, authentication) -> response.sendRedirect("/home");
    }
    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("error", "cannot login with provided credentials");
            response.sendRedirect("/login");
        };
    }
}