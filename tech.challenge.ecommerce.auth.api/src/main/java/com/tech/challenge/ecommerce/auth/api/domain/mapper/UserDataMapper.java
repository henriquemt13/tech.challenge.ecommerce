package com.tech.challenge.ecommerce.auth.api.domain.mapper;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDataMapper {

    @Mapping(source = "userAuth.id", target = "userAuth.id")
    UserData of(SignupRequestDTO signupRequestDTO, UserAuth userAuth);

}
