package org.example;
import java.net.*;
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        RequestHandler r = new RequestHandler();
        System.out.println(r.retrieve("createProfile John%20Doe password123 JohnD"));

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
                System.out.println(line);

                String result = r.retrieve(line);
                System.out.println(result);
                p.println(result);
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
            p.close();
        }
        catch(IOException e)
        {
            System.out.println("broken exit");
        }
    }
}

