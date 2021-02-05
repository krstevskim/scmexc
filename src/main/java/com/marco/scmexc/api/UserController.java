package com.marco.scmexc.api;


import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.dto.UserDto;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<SmxUser> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    private ResponseEntity<SmxUser> getCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        return ResponseEntity.ok(userService.getUserById(userPrincipal.getId()));
    }
}
