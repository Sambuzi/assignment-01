package pcd.ass01.executor;

public class Main {
    public static void main(String[] args) {
        int numBoids = 1000;
        if (args.length > 0) {
            try {
                numBoids = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argomento non valido. Utilizzo del valore predefinito: 500 boids.");
            }
        }

        BoidsModel model = new BoidsModel();
        BoidsExecutorSimulator simulator = new BoidsExecutorSimulator(model);

        simulator.getView().setBoidCount(numBoids);
     
        System.out.println("Simulazione avviata con " + numBoids + " boids...");
        long startTime = System.currentTimeMillis();

        simulator.startSimulation(); 

        long endTime = System.currentTimeMillis();
        System.out.println("Simulazione completata in " + (endTime - startTime) + " ms");
    }
}