package com.example.hooscarserver;
import java.lang.reflect.Array;
import java.util.*;

public class Pool {

    private String ID;
    private List<Profile>() members;
    private List<Profile>() pendingMembers;
    private Profile host;

    public Pool (String id, Profile host) {
        ID = id;
        members = new ArrayList<Profile>();
        pendingMembers = new ArrayList<Profile>();
        this.host = host;
    }

    public String getID() {
        return this.ID;
    }

    public List<Profile>  getMembers() {
        return this.members;
    }

    public List<Profile>  getPendingMembers() {
        return this.pendingMembers;
    }

    public Profile getHost() {
        return host;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void changeHost(Profile host) {
        this.host = host;
    }

    public void addMember(Profile p) {
        members.add(p);
        removePendingMember(p);
    }
    public boolean removeMember(Profile p){
        return members.remove(p);
    }
    public boolean removePendingMember(Profile p)
    {
        return pendingMembers(p);
    }
    public void addPendingMember(Profile p) {
        pendingMembers.add(p);
    }
}
