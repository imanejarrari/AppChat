package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {
    public static void main(String[] args) {
        // Déclarations des variables
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader BR;
        final PrintWriter PW;
        final Scanner scanner = new Scanner(System.in);

        try {
            // Création du ServerSocket avec un port fixe (4000)
            serverSocket = new ServerSocket(4000);
            int port = serverSocket.getLocalPort();
            System.out.println(port);

            // Attente de la connexion du client
            clientSocket = serverSocket.accept();
            
            // Initialisation des flux de communication avec le client
            PW = new PrintWriter(clientSocket.getOutputStream());
            BR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Thread pour envoyer des messages au client
            Thread sender = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    while (true) {
                        // Lire l'entrée utilisateur et envoyer au client
                        msg = scanner.nextLine();
                        PW.println(msg);
                        PW.flush();
                    }
                }
            });
            sender.start();

            // Thread pour recevoir des messages du client
            Thread receive = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        // Lire les messages du client et afficher sur le serveur
                        msg = BR.readLine();
                        while (msg != null) {
                            System.out.println("Client : " + msg);
                            msg = BR.readLine();
                        }

                        // Client déconnecté, fermeture des flux et sockets
                        System.out.println("Client déconnecté");
                        PW.close();
                        scanner.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
