package com.adem.producer.controller;

import com.adem.common.model.AuthenticationUser;
import com.adem.common.model.User;
import com.adem.common.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserServiceController {

    @Autowired
    private UserService userService;


    @PostMapping("find-user-by-id")
    public ResponseEntity<User> findUserById(@Parameter(hidden = true) AuthenticationUser auth,
                                             @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(auth, id));
    }

    @GetMapping("get-user-list")
    public ResponseEntity<List<User>> getUserList(@Parameter(hidden = true) AuthenticationUser auth,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("surname") String surname) {
        return ResponseEntity.ok(userService.getUserList(name, surname, auth));
    }

    @GetMapping("get-nested-user-list")
    public ResponseEntity<List<List<User>>> getNestedUserList(@Parameter(hidden = true) AuthenticationUser authenticationUser,
                                                              @RequestParam("name") String name) {
        return ResponseEntity.ok(userService.getNestedUserList(authenticationUser, name));
    }

    @PostMapping("save-user")
    public ResponseEntity<?> saveUser(@Parameter(hidden = true) AuthenticationUser auth,
                                      @RequestParam("user") User user) {
        userService.saveUser(auth, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("save-user-1")
    public ResponseEntity<?> saveUser(@Parameter(hidden = true) AuthenticationUser auth,
                                      @RequestParam("name") String name,
                                      @RequestParam("surname") String surname,
                                      @RequestParam("id") Long id) {
        userService.saveUser(auth, name, surname, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-user-id")
    public ResponseEntity<Long> getUserId(@Parameter(hidden = true) AuthenticationUser auth,
                                          @RequestParam("username") String username) {
        return ResponseEntity.ok(userService.getUserId(auth, username));
    }


}