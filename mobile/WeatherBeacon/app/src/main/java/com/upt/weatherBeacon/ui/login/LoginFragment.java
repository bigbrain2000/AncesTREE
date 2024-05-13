package com.upt.weatherBeacon.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.data.local.UserSession;
import com.upt.weatherBeacon.data.local.UserSessionManager;
import com.upt.weatherBeacon.databinding.FragmentLoginBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import java.util.Date;

public class LoginFragment extends BaseFragment<LoginViewModel> {

    private FragmentLoginBinding binding;

    private EditText username;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        Button login = binding.login;
        this.password = binding.editTextTextPassword;
        this.username = binding.editTextTextEmailAddress;


        UserSessionManager sessionManager = new UserSessionManager(getContext());
        UserSession session = sessionManager.getUserSession();
        System.out.println("SESSION ::: " + session.username);
        if (session != null && session.username != null && session.username.length() != 0) {
            Date currentDate = new Date();
            Date lastLogin = new Date(session.lastLoginTime);
            if (isDifferenceGreaterThan24Hours(currentDate, lastLogin) == false) {
                viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.HomeMainScreen, null, false)));
            }

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewModel.uiEventStream.setValue(new ShowToast("Login Clicked"));

                viewModel.doLogin(username.getText().toString(), password.getText().toString(), getContext());

//                while(GlobalState.getState().jwtToken == ""){}
//                Navigation navigation = new Navigation(new NavAttribs(Screen.HomeMainScreen, null, true));
//                viewModel.uiEventStream.setValue(navigation);
            }
        });

        return binding.getRoot();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setObservers() {

    }

    @Override
    protected void initUi() {
        TextView register = binding.register;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.RegisterScreen, null, true)));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
