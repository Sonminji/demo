package com.example.demo.authority;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private String id;
    private String credentials;

    public CustomAuthenticationToken(String id, String credentials) {
        super(null);
        this.id = id;
        this.credentials = credentials;
    }

    public CustomAuthenticationToken(String id, String credentials,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id = id;
        this.credentials = credentials;
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
