package com.upt.weatherBeacon.AppState;

import com.upt.weatherBeacon.model.User;

public class GlobalState {
    private static GlobalState state=null;

    private String accessToken="";
    private User usr;
    private String city = "";
    private Double latitude = 0.0;
    private Double longitude = 0.0;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
