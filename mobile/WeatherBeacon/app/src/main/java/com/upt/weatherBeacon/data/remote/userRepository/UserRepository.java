package com.upt.weatherBeacon.data.remote.userRepository;

import com.upt.weatherBeacon.model.User;

public class UserRepository {
    public UserAPI userApi;

    public User getUser(){
        return null;
//        return userApi.getUsers()[0];
    }
    public int editUser(User u){
//        return userApi.updateUser();
        return 1;
    }
    public int createUser(User u){
//        return userApi.register(u);
        return 1;
    }
    public int login(String username, String password){
        return 1;
    }
}
