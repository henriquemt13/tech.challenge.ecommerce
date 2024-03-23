package com.tech.challenge.ecommerce.auth.api.domain.mapper;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T21:57:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class UserAuthMapperImpl implements UserAuthMapper {

    @Override
    public UserAuth of(SignupRequestDTO signupRequestDTO) {
        if ( signupRequestDTO == null ) {
            return null;
        }

        UserAuth userAuth = new UserAuth();

        userAuth.setUsername( signupRequestDTO.getUsername() );
        userAuth.setPassword( signupRequestDTO.getPassword() );
        userAuth.setHasAdminPermission( signupRequestDTO.getHasAdminPermission() );

        return userAuth;
    }
}
