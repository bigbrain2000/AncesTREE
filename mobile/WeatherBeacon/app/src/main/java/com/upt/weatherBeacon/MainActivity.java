package com.upt.weatherBeacon;


import android.os.Bundle;

import com.upt.weatherBeacon.databinding.ActivityMainBinding;
import com.upt.weatherBeacon.ui.base.BaseActivity;
import com.upt.weatherBeacon.ui.base.BaseViewModel;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<BaseViewModel> {

    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void setupUi(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen,null, false)));
    }
}