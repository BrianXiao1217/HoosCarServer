package org.example;
import java.lang.reflect.Array;
import java.util.*;

public class DrivingBlock
{
    private Profile driver;
    private List<Profile> passengers;
    private int passengerLimit;
    private String destination;
    private String date, departTime, returnTime;

    public DrivingBlock()
    {
        driver = null;
        passengers = null;
        passengerLimit = 0;
        destination = "";
        date = "";
        departTime = "";
        returnTime = "";
    }

    public void setDriver(Profile d)
    {
        driver = d;
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
}
