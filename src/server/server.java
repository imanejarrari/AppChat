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
            // Création du ServerSocket avec un port
            serverSocket = new ServerSocket(55202 );
            System.out.println("waiting ...");

            // Attente de la connexion du client
            clientSocket = serverSocket.accept();
            System.out.println("Client connecté");

            // Initialisation des flux de communication avec le client
            PW = new PrintWriter(clientSocket.getOutputStream(), true);
            BR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Thread pour envoyer des messages au client
            Thread sender = new Thread(() -> {
                while (true) {
                    // Lire l'entrée utilisateur et envoyer au client
                    String msg = scanner.nextLine();
                    System.out.print("Imane :");
                    PW.println(msg);
                }
            });
            sender.start();

            // Thread pour recevoir des messages du client
            Thread receiver = new Thread(() -> {
                try {
                    // Lire les messages du client et afficher sur le serveur
                    String msg;
                    while ((msg = BR.readLine()) != null) {
                        System.out.println("Safaa: " + msg);
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
            });
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
