package com.adem.producer.service;

import com.adem.common.model.AuthenticationUser;
import com.adem.common.model.School;
import com.adem.common.model.User;
import com.adem.common.service.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("school.service")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Gson gson = new Gson();

    @Override
    public User findUserById(AuthenticationUser auth, long id) {
        logger.info("::findUserById auth:{} id:{}", gson.toJson(auth), id);
        User user = new User();
        user.setId(id);
        user.setName("adem");
        user.setSurname("bulut");
        return user;
    }

    @Override
    public List<User> getUserList(String name, String surname, AuthenticationUser auth) {
        logger.info("::getUserList name:{} surname:{} auth:{}", name, surname, gson.toJson(auth));
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName(name);
            user.setSurname(surname);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<List<User>> getNestedUserList(AuthenticationUser authenticationUser, String name) {
        List<List<User>> listOfList = new ArrayList<>();

        List<User> list = new ArrayList<>();
        list.add(new User(1, "adem", "bulut", 12, LocalDate.now()));
        list.add(new User(12, "adem", "bulut", 12, LocalDate.now()));
        list.add(new User(13, "adem", "bulut", 12, LocalDate.now()));
        listOfList.add(list);
        return listOfList;
    }

    @Override
    public Map<String, Integer> getUserMapById(long id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Test1", 1);
        map.put("Test11", 11);
        map.put("Test111", 111);
        return map;
    }

    @Override
    public Map<String, List<User>> getStringUserListMap(long id, AuthenticationUser authUser) {
        Map<String, List<User>> map = new HashMap<>();
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "adem", "bulut", 12, LocalDate.now()));
        userList.add(new User(12, "adem", "bulut", 12, LocalDate.now()));
        userList.add(new User(13, "adem", "bulut", 12, LocalDate.now()));
        map.put("adem1", userList);
        map.put("adem2", userList);
        return map;
    }

    @Override
    public void saveUser(AuthenticationUser auth, String name, String surname, long id) {
        logger.info("::saveUser auth:{} name:{} surname:{} id:{}", gson.toJson(auth), name, surname, id);
    }

    @Override
    public void saveUser(AuthenticationUser auth, User user) {
        logger.info("::saveUser auth:{} user:{}", gson.toJson(auth), gson.toJson(user));
    }

    @Override
    public long getUserId(AuthenticationUser auth, String username) {
        logger.info("::getUserId auth:{} username:{}", gson.toJson(auth), username);
        return 12;
    }

    @Override
    public void deleteUserById(AuthenticationUser authenticationUser, long id) {
        logger.info("::deleteUser auth:{} id:{}",gson.toJson(authenticationUser),id);
    }

    @Override
    public School getSchool(AuthenticationUser auth, Map<String, String> userMapping) {
        logger.info("::getSchool auth:{} userMapping:{}", gson.toJson(auth), gson.toJson(userMapping));
        School school = new School();
        school.setName("sample school");
        school.setAddressList(new ArrayList<>());
        return school;
    }

    @Override
    public School[] getSchoolArray(AuthenticationUser authenticationUser) {
        return new School[]{new School(),new School(),new School()};
    }
}
