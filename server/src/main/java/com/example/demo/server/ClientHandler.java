package com.example.demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Handling client: " + clientAddress);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	System.out.println("Received from " + clientAddress + ": " + inputLine);
                out.println("Server received: " + inputLine);
             }

            System.out.println("Client disconnected: " + clientAddress);
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    @Override
//    public void run() {
//        try {
//            // ...
//        	 String clientAddress = clientSocket.getInetAddress().getHostAddress();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Received from " + clientAddress + ": " + inputLine);
//
//             // Reenvía el mensaje a todos los clientes conectados
//                for (PrintWriter writer : TCPServer.clientWriters) {
//                    writer.println("Client " + clientAddress + " says: " + inputLine);
//                }
//            }
//
//            // ...
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
