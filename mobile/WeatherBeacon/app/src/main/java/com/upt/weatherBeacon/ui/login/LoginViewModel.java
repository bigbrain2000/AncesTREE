package com.upt.weatherBeacon.ui.login;

import static com.upt.weatherBeacon.di.NetworkModule.provideUserApiService;

import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.data.remote.userRepository.UserRepository;
import com.upt.weatherBeacon.model.LoginCallback;
import com.upt.weatherBeacon.ui.base.BaseViewModel;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {
    @Inject
    public UserRepository userService;
    GlobalState appState = GlobalState.getState();

    public LoginViewModel(){
        this.userService = new UserRepository();
        this.userService.userApi = provideUserApiService();
    }

    public void doLogin(String username, String password){

        userService.login(username, password, new LoginCallback() {
            @Override
            public void onLoginDataReceived(String token) {
                appState.setJwtToken(token);
                System.out.println("USER::: login viewModel jwt ::: "+ token);
                Navigation navigation = new Navigation(new NavAttribs(Screen.HomeMainScreen, null, true));
                uiEventStream.setValue(navigation);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
