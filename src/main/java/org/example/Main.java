package org.example;
import java.net.*;
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        RequestHandler r = new RequestHandler();
        Processor pr = new Processor();

        System.out.println(r.retrieve("createProfile jd qqq JohnD"));

        //gets IP address of local host
        try
        {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getHostName());
        }
        catch (UnknownHostException e)
        {
            System.exit(0);
        }


        //make sockets
        ServerSocket server = null;
        Socket socket = null;

        //data into server
        DataInputStream in = null;
        //write data out of server
        PrintStream p = null;
        try
        {
            //server socket: sets up the socket for the server to receive stuff
            server = new ServerSocket(80);
            System.out.println("waiting");
            socket = server.accept();
            System.out.println("connected");

            //socket input (reads data from client)
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //socket output (sends data to client)
            p = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("io exception");
            System.exit(0);
        }


        //communicates with client
        pr.process(r, in , p);


        //close socket at end of communicating
        try
        {
            socket.close();
            server.close();
            in.close();
            p.close();
        }
        catch(IOException e)
        {
            System.out.println("broken exit");
        }
    }
}