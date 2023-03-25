package com.example.hooscarclient;
import java.util.*;

public class Profile {
    private String phoneNumber;
    private String username;
    private String displayname;
    private String password;
    private List<String> carPools;
    private List<String> hostedPools;
    public Profile(String phone, String user, String pass){
        phoneNumber = phone;
        username = user;
        password = pass;
        carPools = new ArrayList<String>();
        hostedPools = new ArrayList<String>();
    }
    public Profile(){
        this("","","");
    }
    public void setDisplayname(String display){
        displayname = display;
    }
    public String getDisplayname(String display){
        return displayname;
    }
    public void setPhoneNumber(String phone){
        phoneNumber = phone;
    }
    public void addPool(String id){
        carPools.add(id);
    }
    public List<String> getPools(){
        return carPools;
    }
    public boolean removePools(String id){
        return carPools.remove(id);
    }
    public void addHosted(String id){
        hostedPools.add(id);
    }
    public boolean removeHosted(String id){
        return hostedPools.remove(id);
    }
    public List<String> getHostedPools(){
        return hostedPools;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setUsername(String user){
        username = user;
    }
    public String getUsername(){
        return username;
    }
    public void setPassword(String pass){
        password = pass;
    }
    public String getPassword(){
        return password;
    }

}
