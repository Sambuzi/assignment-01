package pcd.ass01.executor;

public class Main {
    public static void main(String[] args) {
        // Controlla se è stato passato un argomento per il numero di boids
        int numBoids = 1000; // Valore predefinito
        if (args.length > 0) {
            try {
                numBoids = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argomento non valido. Utilizzo del valore predefinito: 500 boids.");
            }
        }

        // Crea il modello e il simulatore
        BoidsModel model = new BoidsModel();
        BoidsExecutorSimulator simulator = new BoidsExecutorSimulator(model);

        // Imposta il numero di boids nella vista
        simulator.getView().setBoidCount(numBoids);

        // Inizia la misurazione del tempo
        System.out.println("Simulazione avviata con " + numBoids + " boids...");
        long startTime = System.currentTimeMillis();

        // Avvia la simulazione
        simulator.startSimulation();

        // Fine della misurazione del tempo
        long endTime = System.currentTimeMillis();
        System.out.println("Simulazione completata in " + (endTime - startTime) + " ms");
    }
}