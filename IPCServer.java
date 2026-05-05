import java.net.*;
import java.io.*;

public class IPCCombined {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                System.out.println("\n**** INTERPROCESS COMMUNICATION ****\n*** SERVER PROCESS STARTED ***\n");
                ServerSocket ss = new ServerSocket(1200);
                Socket s = ss.accept();
                System.out.printf("* Client connected with IP address /%s and port number %d\n\n", s.getInetAddress().getHostAddress(), s.getPort());
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                int a = dis.readInt(), b = dis.readInt();
                System.out.printf("SERVER RECEIVED\nNumber 1 ===> %d\nNumber 2 ===> %d\n\n", a, b);
                int r = a + b;
                dos.writeInt(r);
                System.out.printf("SERVER HAS EXECUTED REQUEST AND SENT RESULT %d TO CLIENT\n\nSERVER PROCESS EXITING...\n", r);
                s.close();
                ss.close();
            } catch (Exception e) { System.out.println("Server Error: " + e); }
        }).start();
        
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Socket s = new Socket("localhost", 1200);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                DataInputStream dis = new DataInputStream(s.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("ENTER VALUE OF NUMBER 1:\n");
                int a = Integer.parseInt(br.readLine());
                System.out.printf("Number 1 ===> %d\n\n", a);
                System.out.print("ENTER VALUE OF NUMBER 2:\n");
                int b = Integer.parseInt(br.readLine());
                System.out.printf("Number 2 ===> %d\n\n", b);
                dos.writeInt(a);
                dos.writeInt(b);
                int r = dis.readInt();
                System.out.printf("CLIENT RECEIVED RESULT FROM SERVER\nTHE ADDITION OF %d AND %d IS %d\n\n", a, b, r);
                s.close();
            } catch (Exception e) { System.out.println("Client Error: " + e); }
        }).start();
    }
}