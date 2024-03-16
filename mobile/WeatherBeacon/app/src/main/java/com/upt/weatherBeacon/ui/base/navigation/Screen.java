package com.upt.weatherBeacon.ui.base.navigation;

import android.window.SplashScreen;

import androidx.fragment.app.Fragment;

import com.upt.weatherBeacon.ui.login.LoginFragment;
import com.upt.weatherBeacon.ui.register.RegisterFragment;

public enum Screen {
    LoginScreen(LoginFragment.class),
    RegisterScreen(RegisterFragment.class);
   public  final Class<? extends Fragment> fragmentClass;

    Screen(Class<? extends  Fragment> fragment){
        this.fragmentClass = fragment;
    }

}
