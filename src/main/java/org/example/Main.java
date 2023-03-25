package org.example;
import java.net.*;
import java.io.*;
public class Main
{
    public static void main(String[] args)
    {
        //gets IP address of local host
        try
        {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getHostName());
        }
        catch (UnknownHostException e)
        { System.exit(0); }

        //make sockets
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream in = null;
        try
        {
            //server socket: sets up the socket for the server to recieve stuff
            server = new ServerSocket(80);
            System.out.println("waiting");
            socket = server.accept();
            System.out.println("connecting");
            //socket input
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch(IOException e)
        {
            System.out.println("io exception");
            System.exit(0);
        }

        //communicates with client
        String line = "";
        while(!line.equals("terminate"))
        {
            try
            {
                line = in.readUTF();
                DrivingBlock d = new DrivingBlock();
                d.setDestination(line);
                System.out.println(line);
                String toString = d.toString();
                System.out.println("toString method called successfully");
                System.out.println(d.toString());
            }
            catch(IOException e)
            {
                System.out.println("bad line");
                System.exit(0);
            }
        }

        //close socket at end of communicating
        try
        {
            socket.close();
            server.close();
            in.close();
        }
        catch(IOException e)
        {
            System.out.println("broken exit");
        }
    }
}