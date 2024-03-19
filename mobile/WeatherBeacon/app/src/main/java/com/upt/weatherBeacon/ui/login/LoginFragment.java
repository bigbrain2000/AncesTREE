package com.upt.weatherBeacon.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentLoginBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;

public class LoginFragment extends BaseFragment<LoginViewModel> {

    private FragmentLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
