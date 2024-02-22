import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private static double globalClock = 0.0;
    private static ArrayList<ReferenceKeeper> references;
    private static ServerSocket welcomeSocket;
    private double localClock = 0;

    public Server() {
        new Clock().start();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Server server;

        System.out.println("Quantidade de clientes?: ");
        int numOfClients = scanner.nextInt();

        server = new Server();

        System.out.println("Iniciando os clientes");
        for (int i = 0; i < numOfClients; i++)
            new Cliente(i, (random.nextDouble() * 10)).start();
        server.synchronizeClock();
    }

    public void synchronizeClock() throws InterruptedException, IOException {
        sendBroadcast();

        receiveConnection();

        Thread.sleep(1000);

        sendAdjustment();
    }

    private void sendBroadcast() {
        byte time[] = (globalClock + "").getBytes();

        try {
            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(time, time.length, address, 6001);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        } catch (UnknownHostException | SocketException e2) {
        } catch (IOException e) {
        }
    }

    private static void receiveConnection() throws IOException {

        welcomeSocket = new ServerSocket(6790);
        references = new ArrayList<>();

        @SuppressWarnings("resource")
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    Socket s = welcomeSocket.accept();
                    new Connection(s).start();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }, 0000, 30);
    }

    private void sendAdjustment() {
        System.out.println("Clientes ativos " + references.size());

        double average, sum = 0;
        for (int j = 0; j < references.size(); j++)
            sum += references.get(j).clientTime;

        average = (sum + localClock) / (references.size() + 1);

        for (int j = 0; j < references.size(); j++) {
            try {
                DataOutputStream outToClient = new DataOutputStream(references.get(j).socket.getOutputStream());
                double difference = average - references.get(j).clientTime;

                outToClient.writeBytes(difference + "\n");

                references.get(j).socket.close();
            } catch (IOException e) {
            }
        }
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

    public static class Connection extends Thread {
        private Socket socket;
        private double clientTime;
        private int position;

        public Connection(int position) {
            this.position = position;
            socket = references.get(position).socket;
        }

        public Connection(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                clientTime = Double.parseDouble(inFromClient.readLine());

                references.add(new ReferenceKeeper(socket, clientTime));
            } catch (IOException e) {
            }
        }
    }

    public static class ReferenceKeeper {
        Socket socket;
        double clientTime;

        public ReferenceKeeper(Socket socket, double difference) {
            this.socket = socket;
            this.clientTime = difference;
        }

        public ReferenceKeeper(Socket socket) {
            this.socket = socket;
        }

        public void setDifference(double difference) {
            this.clientTime = difference;
        }
    }
}