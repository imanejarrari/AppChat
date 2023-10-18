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
	  final ServerSocket serverSocket ;
      final Socket clientSocket ;
      final BufferedReader BR;
      final PrintWriter PW;
      final Scanner scanner=new Scanner(System.in);
      int port=3000;
      try {
		serverSocket=new ServerSocket(port);
    	  clientSocket=serverSocket.accept();
    	  PW=new PrintWriter(clientSocket.getOutputStream());
    	  BR=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	  Thread sender=new Thread(new Runnable() {
    		  String msg;
    		  @Override
    		  public void run() {
    			  while(true) {
    				  msg=scanner.nextLine();
    				  PW.println(msg);
    				  PW.flush();
    			  }
    		  }
    		  
    	  });
    	  sender.start();

          Thread receive= new Thread(new Runnable() {
              String msg ;
              @Override
              public void run() {
                  try {
                      msg = BR.readLine();
                     
                      while(msg!=null){
                          System.out.println("Client : "+msg);
                          msg = BR.readLine();
                      }

                      System.out.println("Client déconecté");

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
