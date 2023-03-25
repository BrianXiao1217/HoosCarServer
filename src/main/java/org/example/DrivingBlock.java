package org.example;
import java.lang.reflect.Array;
import java.util.*;

public class DrivingBlock
{
    private Profile driver;
    private List<Profile> passengers;
    private int passengerLimit;
    private String starting, destination;
    private String date, departTime, returnTime;

    public DrivingBlock()
    {
        driver = new Profile();
        driver.setDisplayname("Needs Driver");
        passengers =  new ArrayList<Profile>();
        passengerLimit = 0;
        starting = destination = "";
        date = "";
        departTime = returnTime = "";
    }

    public void setDriver(Profile d)
    {
        driver = d;
    }
    public void setTrip(String s, String d)
    {
        starting = s;
        destination = d;
    }
    public void setStart(String s)
    {
        starting = s;
    }
    public void setDestination(String d)
    {
        destination = d;
    }
    public void setTime(String d, String dt, String rt)
    {
        date = d;
        departTime = dt;
        returnTime = rt;
    }
    public boolean addPassenger(Profile p)
    {
        if(passengers.size() >= passengerLimit)
            return false;
        return passengers.add(p);
    }
    public void changeCapacity(int i)
    {
        passengerLimit = i;
    }
    public String toString()
    {

        String s = "Driver: "+driver.getDisplayname();
        s+= "\nCapacity: "+passengerLimit;
        s+= "\nPassengers: "+passengers.toString();
        s+= "\nGoing from: "+starting+" to: "+destination;
        s+= "\n Time: "+date+", leaving "+departTime+", returning "+returnTime;
        return s;
    }
}
