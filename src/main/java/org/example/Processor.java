package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Processor
{
    private boolean t = true;

    public synchronized void process(RequestHandler r, DataInputStream in, PrintStream p)
    {
        while(t)
        {
            String line = "";
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
        t = true;

        notifyAll();
    }
    public synchronized void hold()
    {
        while(!t)
        {
            try
            {
                wait();
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
                System.out.println("hold interrupted");
            }
        }

        t = false;

        notifyAll();
    }
}

