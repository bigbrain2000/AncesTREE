package com.upt.weatherBeacon.ui.base.navigation;



import androidx.fragment.app.Fragment;

import com.upt.weatherBeacon.ui.home.HomeMainFragment;
import com.upt.weatherBeacon.ui.login.LoginFragment;
import com.upt.weatherBeacon.ui.register.RegisterFragment;
import com.upt.weatherBeacon.ui.splash.SplashFragment;

public enum Screen {
    LoginScreen(LoginFragment.class),
    RegisterScreen(RegisterFragment.class),
    HomeMainScreen(HomeMainFragment.class),
    SplashScreen(SplashFragment.class);

    public final Class<? extends Fragment> fragmentClass;

    Screen(Class<? extends Fragment> fragment) {
        this.fragmentClass = fragment;
    }

}
