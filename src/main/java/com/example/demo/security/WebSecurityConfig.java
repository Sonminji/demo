package com.example.demo.security;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.example.demo.service.UserService;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests() // 요청에 대한 권한 설정
                .antMatchers("/user/loginUser", "/user/joinUser", "/user","/").permitAll() // 누구나 접근 허용
                .antMatchers("/**").permitAll()
                .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated();

        httpSecurity
                .formLogin() // Form Login 설정
                .loginPage("/user/login")
                .loginProcessingUrl("loginUser")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
