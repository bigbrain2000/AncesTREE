package com.upt.weatherBeacon.data.remote.userRepository;

import com.google.gson.Gson;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.userRepository.dtos.LoginDto;
import com.upt.weatherBeacon.data.remote.userRepository.dtos.LoginDtoBody;
import com.upt.weatherBeacon.data.remote.userRepository.dtos.UserDto;
import com.upt.weatherBeacon.model.GetUserCallback;
import com.upt.weatherBeacon.model.LoginCallback;
import com.upt.weatherBeacon.model.User;

import java.util.Date;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    @Inject
    public UserAPI userApi;

    GlobalState appState = GlobalState.getState();

    public User getUser(String username, GetUserCallback callback) {
        System.out.println("Get User Data callback");
        Call<UserDto> callAsync = userApi.getUser(username);
        callAsync.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                System.out.println("USER::: GET USER INFO SUCCESS " + response.toString());
                if (response.isSuccessful()) {
                    UserDto user = response.body();
                    System.out.println("USER::: " + user.address);
                    callback.onUserDataReceived(mapToUser(user));
                } else {
                    callback.onFailure(new Exception("Failed to load user data"));
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                System.out.println(t);
            }
        });

        return null;
    }

    public int editUser(User u) {

        String requestBodyJson = new Gson().toJson(u);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);

        System.out.println("USER::: REGISTER JSON: " + requestBody);

        return 1;
    }

    public int createUser(User u) {
        String requestBodyJson = new Gson().toJson(u);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);

        System.out.println("USER::: REGISTER JSON: " + requestBodyJson);
        Call<Object> callAsync = userApi.registerUser(requestBody);
        callAsync.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("USER::: REGISTER USER  SUCCESS " + response.toString());
                System.out.println("USER::: REGISTER USER  SUCCESS " + response.body());
//                System.out.println("USER::: REGISTER USER  SUCCESS " + );

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t);
            }
        });
        return 1;
    }
    public int updateUser(User u){
        String requestBodyJson = new Gson().toJson(u);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);


        System.out.println("USER ::: jwt update ::: " + appState);
        Call<Object> callAsync = userApi.updateUser(u.username, requestBody, "Bearer "+appState.getJwtToken().getValue());
        System.out.println("USER ::: password "+u.password);
        System.out.println("USER ::: body "+requestBodyJson);

        callAsync.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("USER::: REGISTER USER  SUCCESS " + response.toString());
                System.out.println("USER::: REGISTER USER  SUCCESS " + response.body());
//                System.out.println("USER::: REGISTER USER  SUCCESS " + );

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t);
            }

        });

        return 1;

    }

    public int login(String username, String password, LoginCallback callback) {

        LoginDtoBody usr = new LoginDtoBody();
        usr.password = password;
        usr.username = username;

        String requestBodyJson = new Gson().toJson(usr);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);

        System.out.println("USER::: LOGIN JSON: " + requestBodyJson);
        Call<LoginDto> callAsync = userApi.loginUser(requestBody);
        callAsync.enqueue(new Callback<LoginDto>() {
            @Override
            public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                System.out.println("USER::: LOGIN USER  SUCCESS " + response.toString());
                if(response.isSuccessful()){
                appState.setJwtToken(response.body().token);
                callback.onLoginDataReceived(response.body().token);}
                else{
                    callback.onFailure(new Throwable("Wrong username/password"));
                }

            }

            @Override
            public void onFailure(Call<LoginDto> call, Throwable t) {
                System.out.println("USER::: "+t);
            }
        });
        return 1;
    }

    private User mapToUser(UserDto usr) {
        User user = new User(usr.username, usr.firstName, usr.lastName, usr.address, usr.email, new Date(), usr.phoneNumber, usr.address);
        return user;
    }
}
