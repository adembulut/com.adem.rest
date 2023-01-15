package com.adem.producer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Parameter;
import com.adem.common.service.UserService;
import com.adem.common.model.User;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.lang.String;
import org.springframework.http.ResponseEntity;
import com.adem.common.model.AuthenticationUser;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/user-service")
public class UserServiceController {

    @Autowired
    private UserService userService;

    @GetMapping("get-user-list")
    public ResponseEntity<List<User>> getUserList(@Parameter(hidden = true)  AuthenticationUser auth,
							 @RequestParam("name")  String name,
							 @RequestParam("surname")  String surname){

        return ResponseEntity.ok(userService.getUserList(name, surname, auth));
    }
    @GetMapping("find-user-by-id")
    public ResponseEntity<User> findUserById(@Parameter(hidden = true)  AuthenticationUser auth,
							 @RequestParam("id")  Long id){

        return ResponseEntity.ok(userService.findUserById(auth, id));
    }
    @GetMapping("get-nested-user-list")
    public ResponseEntity<List<List<User>>> getNestedUserList(@Parameter(hidden = true)  AuthenticationUser authenticationUser,
							 @RequestParam("name")  String name){

        return ResponseEntity.ok(userService.getNestedUserList(authenticationUser, name));
    }
    @GetMapping("get-user-map-by-id")
    public ResponseEntity<Map<String,Integer>> getUserMapById(@RequestParam("id")  Long id){

        return ResponseEntity.ok(userService.getUserMapById(id));
    }
    @GetMapping("get-string-user-list-map")
    public ResponseEntity<Map<String,List<User>>> getStringUserListMap(@Parameter(hidden = true)  AuthenticationUser authUser,
							 @RequestParam("id")  Long id){

        return ResponseEntity.ok(userService.getStringUserListMap(id, authUser));
    }
    @PostMapping("save-user")
    public ResponseEntity<?> saveUser(@Parameter(hidden = true)  AuthenticationUser auth,
							 @RequestParam("user")  User user){

        userService.saveUser(auth, user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("save-user")
    public ResponseEntity<?> saveUser(@Parameter(hidden = true)  AuthenticationUser auth,
							 @RequestParam("name")  String name,
							 @RequestParam("surname")  String surname,
							 @RequestParam("id")  Long id){

        userService.saveUser(auth, name, surname, id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("get-user-id")
    public ResponseEntity<Long> getUserId(@Parameter(hidden = true)  AuthenticationUser auth,
							 @RequestParam("username")  String username){

        return ResponseEntity.ok(userService.getUserId(auth, username));
    }
    @DeleteMapping("delete-user-by-id")
    public ResponseEntity<?> deleteUserById(@Parameter(hidden = true)  AuthenticationUser authenticationUser,
							 @RequestParam("id")  Long id){

        userService.deleteUserById(authenticationUser, id);
        return ResponseEntity.ok().build();
    }


}