package com.tech.challenge.ecommerce.auth.api.domain.mapper;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    UserAuth of(SignupRequestDTO signupRequestDTO);
}
