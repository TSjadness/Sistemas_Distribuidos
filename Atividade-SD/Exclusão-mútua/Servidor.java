import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private int porta;
    private List<Processo> processos;
    private boolean acessoPermitido;

    public Servidor(int porta) {
        this.porta = porta;
        this.processos = new ArrayList<>();
        this.acessoPermitido = true;
    }

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta: " + porta);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new Cliente(socket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void solicitarAcesso(Processo processo) {
        while (!acessoPermitido) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        acessoPermitido = false;
        processos.add(processo);
    }

    public synchronized void liberarAcesso(Processo processo) {
        processos.remove(processo);
        acessoPermitido = true;
        notifyAll();
    }

    // Main method to start the application
    public static void main(String[] args) {
        int porta = 1234; // You can change this to the desired port number
        Servidor servidor = new Servidor(porta);
        servidor.iniciarServidor();
    }
}
