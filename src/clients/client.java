package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        // Déclarations des variables
        final Socket clientSocket;
        final BufferedReader BR;
        final PrintWriter PW;
        final Scanner scanner = new Scanner(System.in);

        try {
            // Connexion au serveur sur un port 
        	clientSocket = new Socket("192.168.1.124",55200);
          //  int port = clientSocket.getLocalPort();
         //   System.out.println(port);

            // Initialisation des flux de communication avec le serveur
            PW = new PrintWriter(clientSocket.getOutputStream());
            BR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Thread pour envoyer des messages au serveur
            Thread sender = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    while (true) {
                        // Lire l'entrée utilisateur et envoyer au serveur
                        msg = scanner.nextLine();
                        PW.println(msg);
                        PW.flush();
                    }
                }
            });
            sender.start();

            // Thread pour recevoir des messages du serveur
            Thread receiver = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        // Lire les messages du serveur et afficher sur le client
                       // msg = BR.readLine();
                      //  while (msg != null) { 
                      //  	msg = BR.readLine();
                    	while ((msg = BR.readLine()) != null) {
                            System.out.println("Imane : " + msg);

                           
                        }

                        // Le serveur est hors service, fermeture des flux et du socket client
                        System.out.println("Server out of service");
                        PW.close();
                        clientSocket.close();
                        scanner.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
