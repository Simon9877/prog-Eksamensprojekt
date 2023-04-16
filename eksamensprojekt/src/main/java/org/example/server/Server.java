package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        final ServerSocket serverSocket;
        final List<PrintWriter> clientOuts = new ArrayList<>();
        PrintWriter serverOut = null;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is running and listening on port 5000...");

            // create a new thread to handle messages from the server console
            Thread consoleThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        String msg = scanner.nextLine();
                        synchronized (clientOuts) {
                            for (PrintWriter clientOut : clientOuts) {
                                clientOut.println("Server: " + msg);
                                clientOut.flush();
                            }
                        }
                    }
                }
            });
            consoleThread.start();

            // infinite loop to keep accepting new client connections
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // create a new thread to handle this client connection
                Thread clientThread = new Thread(new Runnable() {
                    BufferedReader in;
                    PrintWriter out;

                    @Override
                    public void run() {
                        try {
                            out = new PrintWriter(clientSocket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                            // add the client's PrintWriter to the list of clientOuts
                            synchronized (clientOuts) {
                                clientOuts.add(out);
                            }

                            // read messages from the client
                            String msg;
                            while (true) {
                                try {
                                    msg = in.readLine();
                                    if (msg == null) {
                                        // the client has disconnected
                                        break;
                                    }

                                    System.out.println("Client " + clientSocket + ": " + msg);

                                    // send the message to all connected clients (including the server console)
                                    synchronized (clientOuts) {
                                        for (PrintWriter clientOut : clientOuts) {
                                            clientOut.println("Client " + clientSocket + ": " + msg);
                                            clientOut.flush();
                                        }
                                    }
                                } catch (IOException e) {
                                    // the client has disconnected
                                    break;
                                }
                            }

                            // remove the client's PrintWriter from the list of clientOuts
                            synchronized (clientOuts) {
                                clientOuts.remove(out);
                            }

                            out.close();
                            in.close();
                            clientSocket.close();
                            System.out.println("Client disconnected: " + clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
