package edu.neu.neumall.configure;

import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;


    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous()
                .and()
                .authorizeRequests().antMatchers("/login/**", "/register/**", "/img/**", "/js/**", "/css/**", "/fonts/**").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers(HttpMethod.POST, "/product/**").hasAnyAuthority("ADMIN", "SHOPKEEPER")
                .antMatchers(HttpMethod.DELETE, "/product/**").hasAnyAuthority("ADMIN", "SHOPKEEPER")
                .antMatchers("/cart").hasAnyAuthority("CUSTOMER", "ADMIN")
                .antMatchers("/settle/**").hasAnyAuthority("ADMIN", "CUSTOMER", "SHOPKEEPER")
                .antMatchers("/home/**").hasAnyAuthority("ADMIN", "SHOPKEEPER", "CUSTOMER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
