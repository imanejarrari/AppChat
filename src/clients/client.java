package clients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class client {
	public static void main(String[] args) {
		final Socket clientSocket;
		final BufferedReader BR;
		final PrintWriter PW;
		final Scanner scanner=new Scanner(System.in);
		int port=3000;
		String host="127.0.0.1";
		try {
			clientSocket=new Socket(host,port);
			PW=new PrintWriter(clientSocket.getOutputStream());
			BR=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 Thread sender = new Thread(new Runnable() {
	                String msg;
	                @Override
	                public void run() {
	                    while(true){
	                        msg = scanner.nextLine();
	                        PW.println(msg);
	                        PW.flush();
	                    }
	                }
	            });
			 sender.start();
	            Thread receiver = new Thread(new Runnable() {
	                String msg;
	                @Override
	                public void run() {
	                    try {
	                        msg = BR.readLine();
	                        while(msg!=null){
	                            System.out.println("Server : "+msg);
	                            msg = BR.readLine();
	                        }
	                        System.out.println("Server out of service");
	                        PW.close();
	                        clientSocket.close();
	                        scanner.close();
	                  
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            });
	            receiver .start();
	    }catch (IOException e){
	        e.printStackTrace();
	        }
		}
	
}
