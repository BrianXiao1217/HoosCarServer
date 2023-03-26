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
        PrintStream p = null;
        Socket socket = null;
        DataInputStream in = null;
        try
        {
            //server socket: sets up the socket for the server to receive stuff
            server = new ServerSocket(80);
            System.out.println("waiting");
            socket = server.accept();
            System.out.println("connecting");
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
                System.out.println("toString method called successfully");
                p.println(line);
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