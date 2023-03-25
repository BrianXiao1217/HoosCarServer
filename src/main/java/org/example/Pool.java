package org.example;
import java.lang.reflect.Array;
import java.util.*;

public class Pool
{
    private String ID;
    private String name;
    private List<Profile> members;
    private List<Profile> pendingMembers;
    private Profile host;

    public Pool (String id, String n, Profile host)
    {
        ID = id;
        name = n;
        members = new ArrayList<Profile>();
        pendingMembers = new ArrayList<Profile>();
        this.host = host;
    }

    //accessors
    public String getID()
    {
        return this.ID;
    }
    public String getName()
    {
        return name;
    }
    public List<Profile>  getMembers()
    {
        return this.members;
    }
    public List<Profile>  getPendingMembers()
    {
        return this.pendingMembers;
    }
    public Profile getHost()
    {
        return host;
    }

    //modifiers
    public void setID(String ID)
    {
        this.ID = ID;
    }
    public void setName(String n)
    {
        name = n;
    }
    public void changeHost(Profile host)
    {
        this.host = host;
    }

    //add member
    public boolean removePendingMember(Profile p)
    {
        return pendingMembers.remove(p);
    }
    public void addMember(Profile p)
    {
        members.add(p);
        removePendingMember(p);
    }
    public boolean removeMember(Profile p)
    {
        return members.remove(p);
    }
    public void addPendingMember(Profile p)
    {
        pendingMembers.add(p);
    }
}
