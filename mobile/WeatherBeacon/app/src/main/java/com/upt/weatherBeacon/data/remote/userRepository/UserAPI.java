package com.upt.weatherBeacon.data.remote.userRepository;

import com.upt.weatherBeacon.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("users")
    Call<List<User>> getUsers();
//    @POST("")
}
