package com.example.demo.authority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserAuthorityConverter implements AttributeConverter<UserAuthority, String> {
    @Override
    public String convertToDatabaseColumn(UserAuthority auth) {
        if(auth == null){
            return null;
        }

        return auth.getName();
    }

    @Override
    public UserAuthority convertToEntityAttribute(String auth) {
        if(auth == null){
            return null;
        }

        return Stream.of(UserAuthority.values())
                .filter(userRole -> userRole.getName().equals(auth))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
