package io.danielegradassai.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danielegradassai.dto.user.AuthenticationDto;
import io.danielegradassai.dto.user.LoginUserDto;
import io.danielegradassai.dto.user.RegistrationUserDto;
import io.danielegradassai.dto.user.UserOutputDto;
import io.danielegradassai.entity.Role;
import io.danielegradassai.entity.User;
import io.danielegradassai.repository.RoleRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.service.UserService;
import io.danielegradassai.util.JWTUtil;
import io.danielegradassai.util.PasswordUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Override
    public AuthenticationDto login(LoginUserDto userDto) {
        Set<ConstraintViolation<LoginUserDto>> errors = validator.validate(userDto);
        if(!errors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username o password invalidi");
        }
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UTENTE NON TROVATO"));

        try {
            UserOutputDto userOutputDto = modelMapper.map(user, UserOutputDto.class);
            Map<String, String > claimsPrivati = Map.of("user", objectMapper.writeValueAsString(userOutputDto));
            //per far fare a spring il preUpdate
            userRepository.save(user);
            return new AuthenticationDto(jwtUtil.generate(userDto.getEmail(), claimsPrivati), userOutputDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore lato server");
        }
    }

    @Override
    public UserOutputDto signIn(RegistrationUserDto registrationUserDto) {
        Set<ConstraintViolation<RegistrationUserDto>> errors = validator.validate(registrationUserDto);
        if(!errors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campi invalidi: riprova");
        }
        User utente = modelMapper.map(registrationUserDto, User.class);
        utente.setPassword(PasswordUtil.crypt(utente.getPassword()));
        utente.setRoles(Set.of(roleRepository.findByAuthority("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")))));
        return modelMapper.map(userRepository.save(utente), UserOutputDto.class);
    }

    @Override
    public List<UserOutputDto> findAll() {
        return userRepository.findAll().stream().map(m -> modelMapper.map(m, UserOutputDto.class)).toList();
    }

}
