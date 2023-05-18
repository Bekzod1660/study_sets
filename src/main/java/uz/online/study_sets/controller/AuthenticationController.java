package uz.online.study_sets.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.online.study_sets.dto.LoginRequestDto;
import uz.online.study_sets.dto.TokenResponseDto;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.sevice.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "This Controller for login and register")
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary = "This method for post", description = "This method user register")
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(service.register(userDto));
    }

    @Operation(summary = "This method for post", description = "This method user login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
