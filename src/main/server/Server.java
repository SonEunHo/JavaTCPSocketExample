package main.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket socket = null;
        Socket socketConnected = null;
        try {
            socket = new ServerSocket(8383);

            /**
             * Listens for a connection to be made to this socket and accepts
             * it. The method blocks until a connection is made.
             */
            socketConnected = socket.accept();
            InetAddress inetAddressOfClient = socketConnected.getInetAddress();
            System.out.println("Connected!! client socket info : "+inetAddressOfClient.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(socketConnected.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketConnected.getOutputStream()));

            String msgFromClient;
            while((msgFromClient = in.readLine()) != null) {
                System.out.println("received msg from main.client. msg: "+msgFromClient);
                //send msg to main.client
                out.write(msgFromClient.toUpperCase()+"\n");
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("socket err, msg = "+e.getMessage());
        } finally {
            System.out.println("close socket");
            try {
                if(socket != null)
                    socket.close();
                if(socketConnected != null)
                    socketConnected.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
