package io.danielegradassai.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danielegradassai.dto.role.RoleOutputDto;
import io.danielegradassai.dto.user.*;
import io.danielegradassai.entity.Role;
import io.danielegradassai.entity.User;
import io.danielegradassai.repository.RoleRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.service.EmailService;
import io.danielegradassai.service.UserService;
import io.danielegradassai.util.JWTUtil;
import io.danielegradassai.util.PasswordUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Override
    public AuthenticationDto login(LoginUserDto userDto) {
        Set<ConstraintViolation<LoginUserDto>> errors = validator.validate(userDto);
        if(!errors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username o password invalidi");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UTENTE NON TROVATO"));
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password non è valida");
        }

        try {
            UserOutputDto userOutputDto = modelMapper.map(user, UserOutputDto.class);
            Map<String, String > claimsPrivati = Map.of("user", objectMapper.writeValueAsString(userOutputDto));
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
        utente.setActive(true);
        utente.setRoles(Set.of(roleRepository.findByAuthority("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")))));
        return modelMapper.map(userRepository.save(utente), UserOutputDto.class);
    }

    @Override
    public List<RoleOutputDto> findRolesById(Long id) {
        return userRepository.findRolesByUserId(id).stream().map(role -> modelMapper.map(role, RoleOutputDto.class)).toList();
    }

    @Override
    public List<UserOutputDto> findAll() {
        return userRepository.findAll().stream().map(m -> modelMapper.map(m, UserOutputDto.class)).toList();
    }

    @Override
    public List<UserOutputDto> findAllStaff() {
        List<User> staffUsers = userRepository.findByRolesAuthority("ROLE_STAFF");
        return staffUsers.stream()
                .map(user -> modelMapper.map(user, UserOutputDto.class)).toList();
    }

    @Override
    public UserOutputDto staffRegistration(StaffRegistrationDto registrationUserDto) {
        Set<ConstraintViolation<StaffRegistrationDto>> errors = validator.validate(registrationUserDto);
        if(!errors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campi invalidi: riprova");
        }
        User user = modelMapper.map(registrationUserDto, User.class);
        user.setRoles(new HashSet<>(Set.of(
                roleRepository.findByAuthority("ROLE_USER").orElseGet(()->roleRepository.save(new Role("ROLE_USER"))),
                roleRepository.findByAuthority("ROLE_STAFF").orElseGet(()->roleRepository.save(new Role("ROLE_STAFF"))))));
        String password = PasswordUtil.generate();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user = userRepository.save(user);
        emailService.sendConfirmationEmail(user);
        return modelMapper.map(user, UserOutputDto.class);
    }

    @Override
    public void activateUser(Long id) {
        User utente = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attivazione non riuscita: utente non trovato"));
        utente.setActive(true);
        userRepository.save(utente);
    }

    @Override
    public void resetPassword(String newPassword, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        userRepository.deleteById(id);
    }

}
