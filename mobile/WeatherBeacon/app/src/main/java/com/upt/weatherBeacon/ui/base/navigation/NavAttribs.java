package com.upt.weatherBeacon.ui.base.navigation;

public class NavAttribs {
    public Screen screen;
    public BundleProvider bundle = null;
    public Boolean addToBackStack = false;

    public NavAttribs(Screen screen, BundleProvider bp, Boolean back){
        this.screen = screen;
        this.bundle = bp;
        this.addToBackStack = back;

    }

}
