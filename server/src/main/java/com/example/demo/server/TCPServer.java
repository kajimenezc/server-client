package com.example.demo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

	private static final int PORT = 9876;
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public static List<PrintWriter> clientWriters = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(clientWriter);
                
                // Start a new thread to handle the client
                executorService.submit(new ClientHandler(clientSocket, clientWriter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
    public static synchronized void sendMessageToAllClients(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }
    
//    // Método para reenviar mensajes a todos los clientes conectados
//    public static synchronized void broadcast(String message) {
//        for (PrintWriter writer : clientWriters) {
//            writer.println(message);
//        }
//    }
	
}
