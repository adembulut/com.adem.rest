package com.adem.common.service;

import com.adem.common.model.AuthenticationUser;
import com.adem.common.model.School;
import com.adem.common.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUserList(String name, String surname, AuthenticationUser auth);

    User findUserById(AuthenticationUser auth, long id);

    List<List<User>> getNestedUserList(AuthenticationUser authenticationUser, String name);

    Map<String,Integer> getUserMapById(long id);

    Map<String,List<User>> getStringUserListMap(long id , AuthenticationUser authUser);

    void saveUser(AuthenticationUser auth, String name, String surname, long id);

    void saveUser(AuthenticationUser auth, User user);

    long getUserId(AuthenticationUser auth, String username);

    void deleteUserById(AuthenticationUser authenticationUser, long id);

    School getSchool(AuthenticationUser auth, Map<String, String> userMapping);
}
