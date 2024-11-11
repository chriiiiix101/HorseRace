import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Horse implements Runnable {
    private final String name;
    private final int trackLength;
    private int distanceCovered = 0;
    private final Random random = new Random();

    public Horse(String name, int trackLength) {
        this.name = name;
        this.trackLength = trackLength;
    }

    @Override
    public void run() {
        while (distanceCovered < trackLength) {
            int step = random.nextInt(10) + 1; // Avanzamento casuale tra 1 e 10 metri
            distanceCovered += step;
            if (distanceCovered > trackLength) {
                distanceCovered = trackLength;
            }
            System.out.println(name + " ha percorso " + distanceCovered + " metri");
            try {
                Thread.sleep(500); // Pausa di 500 ms per simulare il tempo di avanzamento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " ha raggiunto il traguardo!");
    }
}

public class HorseRace {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci la lunghezza del percorso della gara (in metri): ");
        int trackLength = scanner.nextInt();
        
        System.out.print("Inserisci il numero di cavalli: ");
        int numberOfHorses = scanner.nextInt();
        scanner.nextLine(); // Consuma la linea residua

        List<Thread> horses = new ArrayList<>();

        for (int i = 0; i < numberOfHorses; i++) {
            System.out.print("Inserisci il nome del cavallo " + (i + 1) + ": ");
            String horseName = scanner.nextLine();
            Horse horse = new Horse(horseName, trackLength);
            Thread thread = new Thread(horse);
            horses.add(thread);
        }

        System.out.println("Partenza della gara!");

        // Avvio di tutti i cavalli (thread) contemporaneamente
        for (Thread horse : horses) {
            horse.start();
        }

        // Aspetta che tutti i cavalli completino la corsa
        for (Thread horse : horses) {
            try {
                horse.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("La gara è terminata!");
    }
}
