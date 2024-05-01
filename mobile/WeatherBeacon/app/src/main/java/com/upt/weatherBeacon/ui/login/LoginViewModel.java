package com.upt.weatherBeacon.ui.login;

import com.upt.weatherBeacon.data.remote.userRepository.UserRepository;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {
    @Inject
    public UserRepository userService;

    public void doLogin(String username, String password){
        userService.login(username, password);
    }
}
