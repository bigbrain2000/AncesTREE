package com.upt.weatherBeacon.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.upt.weatherBeacon.ui.base.navigation.UiEvent;

import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    public VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        viewModel.uiEventStream.observe(this, this::processUiEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        setObservers();
    }

    protected abstract void setObservers();

    protected abstract void initUi();

    protected abstract int getContentView();

    private Class<VM> getViewModelClass() {
        ParameterizedType superClassType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<VM>) superClassType.getActualTypeArguments()[0];

    }

    protected void processUiEvent(UiEvent uiEvent) {
        if (this.getActivity() instanceof BaseActivity) {
            ((BaseActivity<?>) this.getActivity()).processUiEvent(uiEvent);
        }
    }
}

