package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Login;
import com.desarrollox.learncompany.web.dto.LoginResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = UserWebMapper.class)
public interface AuthenticationWebMapper {

    LoginResponse toResponse(Login login);

}