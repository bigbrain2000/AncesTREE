package com.upt.weatherBeacon.AppState;

import com.upt.weatherBeacon.model.User;

public class GlobalState {
    private static GlobalState state=null;

    private String accessToken="";
    private User usr;
    private GlobalState(){
  }

    public static synchronized GlobalState getState(){
        if(state == null){
            state = new GlobalState();
        }
        return state;

    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public User getUsr() {
        return usr;
    }
}
