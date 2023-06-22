package io.danielegradassai.service;

import io.danielegradassai.dto.user.AuthenticationDto;
import io.danielegradassai.dto.user.LoginUserDto;
import io.danielegradassai.dto.user.RegistrationUserDto;
import io.danielegradassai.dto.user.UserOutputDto;

import java.util.List;

public interface UserService {
    AuthenticationDto login(LoginUserDto userDto);

    UserOutputDto signIn(RegistrationUserDto registrationUserDto);

    List<UserOutputDto> findAll();
}
