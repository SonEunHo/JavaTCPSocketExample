package main.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //You can change first param(ip adderss) and second param(port)
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8383);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader commandLineInput = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                System.out.print("input msg to send to main.server(to close connection type \"q\" :");
                String msgToServer = commandLineInput.readLine();
                if(msgToServer.toUpperCase().equals("Q"))
                    break;

                out.write(msgToServer+"\n");
                out.flush();

                String msgFromServer = in.readLine();
                System.out.println("msg from main.server :"+msgFromServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("socket err, msg = "+e.getMessage());
        } finally {
            System.out.println("close socket");
            try {
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("socket err, msg = "+e.getMessage());
            }
        }
    }
}
