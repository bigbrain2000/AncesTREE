package com.upt.weatherBeacon;


import android.os.Bundle;
import android.widget.FrameLayout;


import com.upt.weatherBeacon.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<MainViewModel> {

    private FrameLayout frame;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = findViewById(R.id.frame);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void setupUi(){
//        viewModel.uiEventStream =  new Navigation(new NavAttribs(Screen.LoginScreen,null, false));
    }
}