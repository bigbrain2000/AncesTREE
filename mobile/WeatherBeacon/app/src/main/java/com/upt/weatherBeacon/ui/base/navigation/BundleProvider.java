package com.upt.weatherBeacon.ui.base.navigation;

import android.os.Bundle;

public abstract class BundleProvider {
    private Bundle bundle = new Bundle();
    abstract Bundle onAddArgs(Bundle bd);

    public Bundle getBundle(){
        return onAddArgs(bundle);
    }
}
