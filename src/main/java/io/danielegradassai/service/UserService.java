package io.danielegradassai.service;

import io.danielegradassai.dto.role.RoleOutputDto;
import io.danielegradassai.dto.user.*;

import java.util.List;

public interface UserService {
    AuthenticationDto login(LoginUserDto userDto);

    UserOutputDto signIn(RegistrationUserDto registrationUserDto);

    List<RoleOutputDto> findRolesById(Long id);

    List<UserOutputDto> findAll();

    List<UserOutputDto> findAllStaff();

    UserOutputDto staffRegistration(StaffRegistrationDto registrationUserDto);

    void deleteById(Long id);
}
