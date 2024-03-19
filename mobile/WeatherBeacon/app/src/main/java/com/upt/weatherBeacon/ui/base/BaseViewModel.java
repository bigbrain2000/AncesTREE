package com.upt.weatherBeacon.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.upt.weatherBeacon.ui.base.navigation.UiEvent;

public class BaseViewModel extends ViewModel {
    public MutableLiveData<UiEvent> uiEventStream = new MutableLiveData<>();
}
