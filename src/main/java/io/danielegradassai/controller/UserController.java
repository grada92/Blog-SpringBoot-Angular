package io.danielegradassai.controller;

import io.danielegradassai.dto.user.AuthenticationDto;
import io.danielegradassai.dto.user.LoginUserDto;
import io.danielegradassai.dto.user.RegistrationUserDto;
import io.danielegradassai.dto.user.UserOutputDto;
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

    @GetMapping()
    public ResponseEntity<List<UserOutputDto>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

}
