import java.util.Random;

class SapoThread extends Thread {
    private static final int DISTANCIA_MAXIMA = 100;  // Distância máxima da corrida
    private static final int TAMANHO_MAXIMO_PULO = 10;  // Tamanho máximo de cada pulo

    private String nome;
    private int distanciaPercorrida;

    public SapoThread(String nome) {
        this.nome = nome;
        this.distanciaPercorrida = 0;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distanciaPercorrida < DISTANCIA_MAXIMA) {
            int tamanhoPulo = random.nextInt(TAMANHO_MAXIMO_PULO + 1);
            distanciaPercorrida += tamanhoPulo;
            System.out.println(nome + " pulou " + tamanhoPulo + " metros. Distância percorrida: " + distanciaPercorrida + " metros.");

            try {
                // Adiciona um pequeno atraso para simular o tempo entre os pulos dos sapos
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(nome + " chegou! Colocação: " + obterColocacao());
    }

    private String obterColocacao() {
        return switch (distanciaPercorrida) {
            case DISTANCIA_MAXIMA -> "1º lugar!";
            default -> "2º lugar ou além...";
        };
    }
}

public class CorridaDeSapos {
    public static void main(String[] args) {
        // Criar threads para controlar cada sapo
        SapoThread sapo1 = new SapoThread("Sapo1");
        SapoThread sapo2 = new SapoThread("Sapo2");
        SapoThread sapo3 = new SapoThread("Sapo3");
        SapoThread sapo4 = new SapoThread("Sapo4");
        SapoThread sapo5 = new SapoThread("Sapo5");

        // Iniciar as threads
        sapo1.start();
        sapo2.start();
        sapo3.start();
        sapo4.start();
        sapo5.start();
    }
}
