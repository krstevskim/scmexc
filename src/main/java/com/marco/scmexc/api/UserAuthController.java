package com.marco.scmexc.api;

import com.marco.scmexc.models.auth.JwtAuthenticationResponse;
import com.marco.scmexc.models.auth.LoginPayload;
import com.marco.scmexc.models.dto.UserDto;
import com.marco.scmexc.models.response.UserResponse;
import com.marco.scmexc.service.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class UserAuthController {

    private final UserAuthenticationService service;

    public UserAuthController(UserAuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    private ResponseEntity loginUser(@RequestBody LoginPayload payload) {
        JwtAuthenticationResponse token = service.authenticateUser(payload);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    private UserResponse registerUser(@RequestBody UserDto newUser) {
        return service.registerUser(newUser);
    }
}
