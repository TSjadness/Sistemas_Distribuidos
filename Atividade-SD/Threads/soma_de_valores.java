import java.util.Random;

class SomaLinhaThread extends Thread {
    private int[][] matriz;
    private int linha;
    private int[] resultado;

    public SomaLinhaThread(int[][] matriz, int linha, int[] resultado) {
        this.matriz = matriz;
        this.linha = linha;
        this.resultado = resultado;
    }

    @Override
    public void run() {
        int soma = 0;
        for (int valor : matriz[linha]) {
            soma += valor;
        }
        resultado[linha] = soma;
        System.out.println(Thread.currentThread().getName() + ": Soma da linha " + (linha + 1) + " = " + soma);
    }
}

public class Main {
    public static void main(String[] args) {
        // Criar uma matriz 3x5 com números aleatórios entre 1 e 10
        int[][] matriz = new int[3][5];
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                matriz[i][j] = random.nextInt(10) + 1;
            }
        }

        // Lista para armazenar os resultados das somas das linhas
        int[] resultados = new int[3];

        // Criar três threads, uma para cada linha
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new SomaLinhaThread(matriz, i, resultados);
        }

        // Iniciar as threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Aguardar até que todas as threads terminem
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimir a matriz e os resultados
        System.out.println("\nMatriz:");
        for (int[] linha : matriz) {
            for (int valor : linha) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }

        System.out.println("\nResultados:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Soma da linha " + (i + 1) + " = " + resultados[i]);
        }
    }
}
