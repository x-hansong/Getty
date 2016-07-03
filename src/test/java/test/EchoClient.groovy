package test;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        String serverHostname = new String ("127.0.0.1");

        if (args.length > 0)
            serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port 4399.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader input = null;

        try {
            echoSocket = new Socket(serverHostname, 4399);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        System.out.println ("Type Message (\"Bye.\" to quit)");
        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);

            // end loop
            if (userInput.equals("Bye."))
                break;

            System.out.println("echo: " + input.readLine());
        }

        Thread.sleep(1000);

        out.close();
        input.close();
        stdIn.close();
        echoSocket.close();
    }
}
