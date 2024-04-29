package com.upt.weatherBeacon.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.databinding.FragmentSplashScreenBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

public class SplashFragment  extends BaseFragment<SplashViewModel> {
    private FragmentSplashScreenBinding binding;
    private GlobalState appState = GlobalState.getState();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSplashScreenBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
    @Override
    protected void setObservers() {

    }
    @Override
    public void onResume(){
        super.onResume();

        // Start waiting for the condition
        startWaiting();
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected int getContentView() {
        return 0;
    }

    private void startWaiting() {
        // Implement the condition check
        if (appState.getLatitude() != 0.0) {
            // If the condition is already met, navigate immediately
            viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
        } else {
            // Otherwise, start waiting for the condition
            final Handler handler = new Handler(Looper.getMainLooper());
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (appState.getLatitude() != 0.0) {
                        viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));

                    } else {
                        // Continue waiting
                        handler.postDelayed(this, 500); // Check every 100 milliseconds
                    }
                }
            };
            handler.post(runnable);
        }
    }
}
