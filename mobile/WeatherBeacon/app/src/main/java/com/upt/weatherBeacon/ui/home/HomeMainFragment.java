package com.upt.weatherBeacon.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;

public class HomeMainFragment extends BaseFragment<HomeViewModel> {

    private FragmentHomemainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomemainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setObservers() {

    }

    @Override
    protected void initUi() {

    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
