package com.upt.weatherBeacon;


import android.os.Bundle;

import com.upt.weatherBeacon.data.remote.userRepository.OpenMeteoApi;
import com.upt.weatherBeacon.databinding.ActivityMainBinding;
import com.upt.weatherBeacon.di.Config;
import com.upt.weatherBeacon.ui.base.BaseActivity;
import com.upt.weatherBeacon.ui.base.BaseViewModel;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<BaseViewModel> {

    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        OpenMeteoApi service = retrofit.create(OpenMeteoApi.class);
        Call<Object> callAsync = service.getResponse("37.7749", "-122.4194", "temperature_2m", "temperature_2m", 1);
//getResposne("52.52", "12.419");

        callAsync.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object resp = response.body();
                System.out.println("Response: " + response.code());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void setupUi() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
    }
}