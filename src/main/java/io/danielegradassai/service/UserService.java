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

    void activateUser(Long id);

    void resetPassword(String newPassword, Long id);

    void deleteById(Long id);

    void blockUser(Long id);

    void activeUser(Long id);

    UserOutputDto getUser(Long userId);

    void subscribeToNotifications(Long userId);

}
