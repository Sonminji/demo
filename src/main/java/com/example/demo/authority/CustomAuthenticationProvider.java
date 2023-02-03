package com.example.demo.authority;

import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider{


    @Resource
    private UserService userService;


    private PasswordEncoder passwordEncoder;

    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;


        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetailsDTO userDetailsDto = (UserDetailsDTO) userService.loadUserByUsername(userId);

        //평문을 암호화하여 비밀번호 비교
        if(!passwordEncoder.matches(userPw,userDetailsDto.getPassword())){
            throw new BadCredentialsException(userDetailsDto.getUsername() + "Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetailsDto, userPw, userDetailsDto.getAuthorities());
    }

    private boolean matchPassword(String loginPw, String password) {
        return loginPw.equals(password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
