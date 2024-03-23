package com.tech.challenge.ecommerce.auth.api.domain.mapper;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T21:57:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class UserDataMapperImpl implements UserDataMapper {

    @Override
    public UserData of(SignupRequestDTO signupRequestDTO, UserAuth userAuth) {
        if ( signupRequestDTO == null && userAuth == null ) {
            return null;
        }

        UserData userData = new UserData();

        if ( signupRequestDTO != null ) {
            userData.setEmail( signupRequestDTO.getEmail() );
        }
        if ( userAuth != null ) {
            userData.setUserAuth( userAuthToUserAuth( userAuth ) );
            userData.setId( userAuth.getId() );
        }

        return userData;
    }

    protected UserAuth userAuthToUserAuth(UserAuth userAuth) {
        if ( userAuth == null ) {
            return null;
        }

        UserAuth userAuth1 = new UserAuth();

        userAuth1.setId( userAuth.getId() );

        return userAuth1;
    }
}
