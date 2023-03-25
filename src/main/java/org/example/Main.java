package org.example;
import java.net.*;
import java.io.*;
public class Main {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getHostName());
        }catch (UnknownHostException e){
            System.exit(0);
        }
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream in = null;
        try {
            server = new ServerSocket(80);
            System.out.println("waiting");
            socket = server.accept();
            System.out.println("connecting");
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
        } catch(IOException e){
            System.out.println("fuck you");
            System.exit(0);
        }

        String line = "";
        while(!line.equals("terminate")){
            try{
                line = in.readUTF();
                System.out.println(line);
            } catch(IOException e){
                System.out.println("bad line");
            }
        }
        try{
            socket.close();
            server.close();
            in.close();
        } catch(IOException e){
            System.out.println("broken exit");
        }
    }
}