package io.danielegradassai.controller;

import io.danielegradassai.dto.role.RoleOutputDto;
import io.danielegradassai.dto.user.*;
import io.danielegradassai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<UserOutputDto> sign(@RequestBody RegistrationUserDto registrationUserDto){
        return new ResponseEntity<>(userService.signIn(registrationUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginUserDto loginUserDto){
        return new ResponseEntity<>(userService.login(loginUserDto), HttpStatus.OK);
    }

    @PostMapping("/authorRegistration")
    public ResponseEntity<UserOutputDto> staffRegistration(@RequestBody StaffRegistrationDto registrationUserDto){
        return new ResponseEntity<>(userService.staffRegistration(registrationUserDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<UserOutputDto>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleOutputDto>> findRolesById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findRolesById(id), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public List<UserOutputDto> getAllAuthorUsers() {
        return userService.findAllStaff();
    }

    @DeleteMapping("/deleteAuthor/{id}")
    public ResponseEntity<Void> deleteUserStaff(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
