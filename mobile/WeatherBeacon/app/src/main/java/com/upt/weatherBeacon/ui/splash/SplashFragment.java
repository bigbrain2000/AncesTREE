package com.upt.weatherBeacon.ui.splash;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.local.UserSession;
import com.upt.weatherBeacon.data.local.UserSessionManager;
import com.upt.weatherBeacon.databinding.FragmentSplashScreenBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import java.util.Date;

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
            Handler h1 = new Handler(Looper.getMainLooper());
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Otherwise, start waiting for the condition
                    final Handler handler = new Handler(Looper.getMainLooper());
                    final Runnable runnable = new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            if (appState.getLatitude() != 0.0) {
                                viewModel.getWeatherDataForCurrentLocation();
                                viewModel.getUserData();

                                UserSessionManager sessionManager = new UserSessionManager(getContext());
                                UserSession session = sessionManager.getUserSession();
                                System.out.println("SESSION ::: "+session.username);
                                if (session != null && session.username != null && session.username.length() != 0) {
                                    Date currentDate = new Date();
                                    Date lastLogin = new Date(session.lastLoginTime);
                                    if (isDifferenceGreaterThan24Hours(currentDate, lastLogin) == false) {
                                        viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.HomeMainScreen, null, false)));
                                    }
                                    else{
                                        viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
                                    }
                                } else {
                                    viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
                                }
                            } else {
                                // Continue waiting
                                handler.postDelayed(this, 1000); // Check every 100 milliseconds
                            }
                        }
                    };
                    handler.post(runnable);
                }

            }, 1500);
        }

    }

    private boolean isDifferenceGreaterThan24Hours(Date date1, Date date2) {
        // Convert Date objects to milliseconds
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        // Calculate the difference in milliseconds
        long diffMillis = Math.abs(time1 - time2);

        // Convert difference to hours
        long diffHours = diffMillis / (1000 * 60 * 60);

        // Check if difference is greater than 23 hours
        return diffHours > 23;
    }
}
