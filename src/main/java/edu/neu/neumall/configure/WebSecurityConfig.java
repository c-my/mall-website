package edu.neu.neumall.configure;

import edu.neu.neumall.handler.AuthEntryPoint;
import edu.neu.neumall.handler.AuthFailureHandler;
import edu.neu.neumall.handler.AuthSuccessHandler;
import edu.neu.neumall.handler.ContentAccessDeniedHandler;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    private AuthSuccessHandler authSuccessHandler;

    private AuthFailureHandler authFailureHandler;

    private ContentAccessDeniedHandler contentAccessDeniedHandler;

    private AuthEntryPoint authEntryPoint;


    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
                             AuthSuccessHandler authSuccessHandler,
                             AuthFailureHandler authFailureHandler,
                             ContentAccessDeniedHandler contentAccessDeniedHandler,
                             AuthEntryPoint authEntryPoint) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;

        this.authFailureHandler = authFailureHandler;
        this.authSuccessHandler = authSuccessHandler;
        this.contentAccessDeniedHandler = contentAccessDeniedHandler;
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**","/css/**", "/js/**", "/product", "favicon.ico", "/register", "/login").permitAll()
//                .antMatchers("/img/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/img/**").hasRole("SHOPKEEPER")
//                .antMatchers("/product/purchase").hasRole("CUSTOMER")
//                .anyRequest().authenticated()

                .and()
                .csrf().disable()

                .formLogin()
                .loginPage("/login")
                .failureHandler(authFailureHandler)
                .successHandler(authSuccessHandler)
                .permitAll()

                .usernameParameter("username").passwordParameter("password")

                .and().logout().permitAll();

        http.exceptionHandling()
                .accessDeniedHandler(contentAccessDeniedHandler)
                .authenticationEntryPoint(authEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

}

