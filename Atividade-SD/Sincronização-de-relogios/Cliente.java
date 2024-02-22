import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.Socket;

public class Cliente extends Thread {
    private int processId;
    private double localClock = 0;

    public Cliente(int processId) {
        this.processId = processId;
    }

    public Cliente(int processId, double initialClock) {
        this.processId = processId;
        this.localClock = initialClock;
    }

    public void run() {
        new Clock().start();
        String data = null;

        try {
            MulticastSocket multicastSocket = new MulticastSocket(6001);
            byte receive[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(receive, receive.length);
            multicastSocket.receive(packet);
            data = new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
        }

        if (data != null) {
            try {
                Socket clientSocket = new Socket("127.0.0.1", 6790);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeBytes(localClock + "\n");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                double adjustment = Double.parseDouble(inFromClient.readLine());
                System.out.print("ID: " + processId
                        + "\n Tempo ajustado: " + adjustment
                        + "\n Antes do ajuste: " + localClock);

                localClock += adjustment;
                System.out.println();

                System.out.println("Após o ajuste: " + localClock);
            } catch (IOException e) {
            }
        } else
            System.out.println("Nenhuma solicitação de transmissão recebida");
    }

    public class Clock extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                    localClock += 0.1;
                } catch (InterruptedException e) {
                }
            }
        }
    }
}