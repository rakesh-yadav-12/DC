import java.net.*;
import java.io.*;

public class IPCClient {
    public static void main(String args[]) {

        try {
            Socket s = new Socket("localhost", 1200);

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());

            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            System.out.println("\n************* CLIENT PROCESS STARTED **********************");
            System.out.println("\nENTER VALUE OF NUMBER 1:");
            int a = Integer.parseInt(br.readLine());
            System.out.println("Number 1 ====> " + a);
            dos.writeInt(a);

            System.out.println("\nENTER VALUE OF NUMBER 2:");
            int b = Integer.parseInt(br.readLine());
            System.out.println("Number 2 ====> " + b);
            dos.writeInt(b);

            int result = dis.readInt();

            System.out.println("\nCLIENT RECEIVED RESULT FROM SERVER");
            System.out.println("THE ADDITION OF " + a + " AND " + b + " IS " + result);

            s.close();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
