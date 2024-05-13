package com.upt.weatherBeacon.ui.register;

import static com.upt.weatherBeacon.di.NetworkModule.provideUserApiService;

import com.upt.weatherBeacon.data.remote.userRepository.UserRepository;
import com.upt.weatherBeacon.model.User;
import com.upt.weatherBeacon.ui.base.BaseViewModel;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegisterViewModel extends BaseViewModel {

    @Inject
    public UserRepository repository;
    private String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";

    public RegisterViewModel(){
        this.repository = new UserRepository();
        this.repository.userApi = provideUserApiService();
    }

    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        System.out.println("VALIDATE EMAIL: "+ matcher.matches());
        return matcher.matches();
    }
    public boolean validatePassword(String password){
        return password.length() >= 8;
    }
    public boolean registerUser(String username, String firstName, String lastName, String email, String password, Date birthDate, String phoneNumber){

        User newUser = new User(username, firstName, lastName, password, email, birthDate, phoneNumber, "aaa");
        System.out.println("USER::: AM AJUNS AICI password " + password);
        int ok = repository.createUser(newUser);

        return ok == 1;
    }
}
