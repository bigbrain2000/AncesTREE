package com.upt.weatherBeacon.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentLoginBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.ShowToast;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

public class LoginFragment extends BaseFragment<LoginViewModel> {

    private FragmentLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        Button login = binding.login;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.uiEventStream.setValue(new ShowToast("CEVA"));
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
                viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.RegisterScreen,null, false)));
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
