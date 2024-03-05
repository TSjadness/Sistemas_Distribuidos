import java.io.*;
import java.net.*;

public class Cliente implements Runnable {
    private Socket socket;
    private Servidor servidor;

    public Cliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            Processo processo = (Processo) input.readObject();
            servidor.solicitarAcesso(processo);

            // Simulação da região crítica
            System.out.println("Processo " + processo.getId() + " entrou na região crítica.");
            Thread.sleep(1000); // Simulação de tempo de execução na região crítica
            System.out.println("Processo " + processo.getId() + " saiu da região crítica.");

            servidor.liberarAcesso(processo);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Main method for standalone execution
    public static void main(String[] args) {
        // You can add code here if needed for standalone execution
    }
}
