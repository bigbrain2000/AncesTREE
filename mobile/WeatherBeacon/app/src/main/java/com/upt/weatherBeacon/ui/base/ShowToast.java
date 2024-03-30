package com.upt.weatherBeacon.ui.base;

import com.upt.weatherBeacon.ui.base.navigation.UiEvent;

public class ShowToast extends UiEvent {
    public String message;

    public ShowToast(String message) {
        this.message = message;
    }
}
