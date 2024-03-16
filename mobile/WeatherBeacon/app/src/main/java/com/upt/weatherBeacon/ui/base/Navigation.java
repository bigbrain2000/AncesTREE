package com.upt.weatherBeacon.ui.base;

import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.UiEvent;



 public class Navigation extends UiEvent {
  public   NavAttribs navAttirbs;

     public  Navigation(NavAttribs navAttribs) {
        this.navAttirbs = navAttribs;
    }
}

  class NavBack extends UiEvent {
}
  class GoToMain extends UiEvent{}

  class ShowToast extends UiEvent{
   public  String message;
      ShowToast(String message){
        this.message = message;
    }
}

  class ShowLoading extends UiEvent{}

  class HideLoading extends UiEvent{}
