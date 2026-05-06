import java.net.*;
import java.io.*;

public class IPCServer {
    public static void main(String args[]) {

        System.out.println("\n**** INTERPROCESS COMMUNICATION ****");
        System.out.println("\n*** SERVER PROCESS STARTED ***");

        System.out.println("\n* SERVER IS READY AND WAITING TO RECEIVE DATA FROM CLIENT PROCESS ON PORT 1200");

        try {
            ServerSocket ss = new ServerSocket(1200); // Port number
            Socket clientSocket = ss.accept();

            System.out.println("\n* Client connected with IP address "
                    + clientSocket.getInetAddress()
                    + " and port number "
                    + clientSocket.getPort());

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            int a = dis.readInt();
            System.out.println("\nSERVER RECEIVED");
            System.out.println("Number 1 ====> " + a);

            int b = dis.readInt();
            System.out.println("Number 2 ====> " + b);

            int c = a + b;
            dos.writeInt(c);

            System.out.println("\nSERVER HAS EXECUTED REQUEST AND SENT RESULT " + c + " TO CLIENT");

            clientSocket.close();
            ss.close();

            System.out.println("\nSERVER PROCESS EXITING...");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
