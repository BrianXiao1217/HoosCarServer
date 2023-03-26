package org.example;
import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RequestHandler
{
    private List<Pool> allPools = null;
    private List<Profile> allProfiles = null;
    private Profile currentProfile = null;
    private Pool currentPool = null;
    public RequestHandler()
    {
        allPools = new ArrayList<Pool>();
        allProfiles = new ArrayList<Profile>();
        loadRecordedData();
    }
    public void loadRecordedData()
    {
        //stub for now, later to load data for server restarting
        Profile sampleHost = new Profile("703-703-7037", "SOCKCAT12", "pass321", "Socks");
        Pool samplePool = new Pool("qhu2fg","Pool", sampleHost);
        sampleHost.addPool("qhu2fg");
        sampleHost.addHosted("qhu2fg");
        allPools.add(samplePool);
        allProfiles.add(sampleHost);
    }
    public String createProfile(String user, String pass, String display)
    {
        List<Profile> filtered = allProfiles.stream()
                .filter(s -> s.getUsername().equals(user))
                .collect(toList());
        if(filtered.size() < 1)
        {
            Profile newUser = new Profile("Phone#Stub", user, pass, display);
            allProfiles.add(newUser);
            return "success";
        }
        return "failed";
    }
    public String tryLogin(String user, String pass)
    {
        List<Profile> filtered = allProfiles.stream()
                .filter(s -> s.getUsername().equals(user))
                .collect(toList());
        if(filtered.size() == 1)
        {
            Profile attemptedLogin = filtered.get(0);
            if(attemptedLogin.getPassword().equals(pass))
            {
                currentProfile = attemptedLogin;
                return "success";
            }
        }
        return "failureLogin";
    }
    public String getProfilePools()
    {
        if(currentProfile == null)
            return "failureGetProfilePools";
        return "success";
    }
    public String joinPool(String pool_id)
    {
        if(currentProfile == null)
            return "failureJoinPool_NoCurrentProfile";
        List<Pool> filtered = allPools.stream()
                .filter(s -> s.getID().equals(pool_id))
                .collect(toList());
        if(filtered.size() == 1)
        {
            filtered.get(0).addPendingMember(currentProfile);
            return "success";
        }
        return "failureJoinPool_end";
    }
    public String showPendingMembers()
    {
        if(currentProfile == null || currentPool == null)
            return "failureShowPending_allNull";
        List<Pool> filtered = allPools.stream()
                .filter(s -> s.getID().equals(currentPool.getID()))
                .collect(toList());
        if(filtered.get(0).getHost().getUsername().equals(currentProfile.getUsername()))
        {
            List<Profile> pendingProfiles = filtered.get(0).getPendingMembers();
            String answer = "";
            int k = 0;
            while(true)
            {
                answer += pendingProfiles.get(k).getUsername();
                k++;
                if(pendingProfiles.size() <= k)
                    return answer;
                answer += " ";
            }
        }
        return "failureShowPending_end";
    }
    public String selectPool(String pool_id)
    {
        if(currentProfile == null)
            return "failureSelectPool_NoCurrentProfile";
        List<Pool> filteredPools = allPools.stream()
                .filter(s -> s.getID().equals(pool_id))
                .collect(toList());
        if(filteredPools.size() == 0)
            return "failureSelectPool_sizePools=0";
        Pool attemptedPool = filteredPools.get(0);
        List<Profile> filteredProfiles = attemptedPool.getMembers().stream()
                .filter(s -> s.getUsername().equals(currentProfile.getUsername()))
                .collect(toList());
        if(filteredProfiles.size() == 0)
            return "failureSelectPool_sizeProfiles=0";
        currentPool = attemptedPool;
        return "success";
    }
    public String allowPending(String user)
    {
        if(currentProfile == null || currentPool == null)
            return "failureAllowPending_null";
        List<Pool> filtered = allPools.stream()
                .filter(s -> s.getID().equals(currentPool.getID()))
                .collect(toList());
        //System.out.println(currentPool.getPendingMembers().size());
        List<Profile> filteredProfiles = currentPool.getPendingMembers().stream()
                .filter(u -> u.getUsername().equals(user))
                .collect(toList());
        if(filteredProfiles.size() == 0)
            return "failureAllowPending_size=0";
        Profile toAdd = filteredProfiles.get(0);
        if(filtered.get(0).getHost().getUsername().equals(currentProfile.getUsername()))
        {
            currentPool.addMember(toAdd);
            toAdd.addPool(filtered.get(0).getID());
            return "success";
        }
        return "failureAllowPending_end";
    }
    public String retrieve(String command)
    {
        String[] com = command.split(" ");
        if(com.length < 1)
            return "failureRetrieve";
        String order = com[0];
        switch(order)
        {
            case "createProfile":
                return this.createProfile(com[1],com[2],com[3]);
            case "login":
                return this.tryLogin(com[1],com[2]);
            case "getAllPools":
                return this.getProfilePools();
            case "join":
                return this.joinPool(com[1]);
            case "showPending":
                return this.showPendingMembers();
            case "allowPending":
                return this.allowPending(com[1]);
            case "selectPool":
                return this.selectPool(com[1]);
            case "getPeople":
                return "";
            case "getBlocks":
                return "";
            case "getDrivingBlockPassengers":
                return "";
            //TODO: deletePool, deleteProfile, createPool
            default:
                return "failureRetrieve_SwitchCase                                                                      ";
        }
    }

    public static void main(String[] args)
    {
        RequestHandler handler = new RequestHandler();
        System.out.println("attempting to create profile...");
        System.out.println(handler.retrieve("createProfile John%20Doe password123 JohnD"));
        System.out.println("attempting login attempt with wrong password...");
        System.out.println(handler.retrieve("login jhndoe wrongpassword"));
        System.out.println("attempting to login with the profile...");
        System.out.println(handler.retrieve("login jhndoe password123"));
        System.out.println("attempting to join existing pool...");
        System.out.println(handler.retrieve("join qhu2fg"));
        System.out.println("Making another account, logging in, attempting to join existing pool");
        System.out.println(handler.retrieve("createProfile janedo Jane%20Doe pass123"));
        System.out.println(handler.retrieve("login janedo pass123"));
        System.out.println(handler.retrieve("join qhu2fg"));
        System.out.println("logging in to host account...");
        System.out.println(handler.retrieve("login SOCKCAT12 pass321"));
        System.out.println("viewing existing pool...");
        System.out.println(handler.retrieve("selectPool qhu2fg"));
        System.out.println("viewing pending members...");
        System.out.println(handler.retrieve("showPending"));
        System.out.println("accepting jane...");
        System.out.println(handler.retrieve("allowPending janedo"));
        System.out.println("logging into Jane's account and attempting to access the group...");
        System.out.println(handler.retrieve("login janedo pass123"));
        System.out.println(handler.retrieve("selectPool qhu2fg"));

    }
}
