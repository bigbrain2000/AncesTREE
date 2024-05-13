package com.upt.weatherBeacon.data.remote.userRepository;

import com.upt.weatherBeacon.data.remote.userRepository.dtos.LoginDto;
import com.upt.weatherBeacon.data.remote.userRepository.dtos.UserDto;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    //    @GET("users")
//    Call<List<User>> getUsers();
//    @POST("")
    @POST("register")
    Call<Object> registerUser(@Body RequestBody requestBody);

    @POST("auth/login")
    Call<LoginDto> loginUser(@Body RequestBody requestBody);

    @PATCH("user/{username}")
    Call<Object> updateUser(@Path("username") String username, @Body RequestBody requestBody, @Header("Authorization") String jwtToken);

    @DELETE("user/{username}")
    Call<Object> deleteUser(@Path("username") String username,@Header("Authorization") String jwtToken);

    @GET("user/{username}")
    Call<UserDto> getUser(@Path("username") String username);




}
