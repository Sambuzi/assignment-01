package pcd.ass01.multithread;

/**
 * The entry point for the boids simulation using multithreading.
 * This class initializes the model, simulator, and starts the simulation.
 */
public class Main {
    public static void main(String[] args) {
        // Default number of boids
        int numBoids = 100;
        
        // Parse the number of boids from command-line arguments
        if (args.length > 0) {
            try {
                numBoids = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid argument. Using default value: 100 boids.");
            }
        }

        // Create the model and simulator
        BoidsModel model = new BoidsModel();
        BoidsSimulator simulator = new BoidsSimulator(model);

        // Set the number of boids in the view
        simulator.getView().setBoidCount(numBoids);

        // Start measuring execution time
        System.out.println("Simulation started with " + numBoids + " boids...");
        long startTime = System.currentTimeMillis();

        // Start the simulation
        simulator.startSimulation();

        // End measuring execution time
        long endTime = System.currentTimeMillis();
        System.out.println("Simulation completed in " + (endTime - startTime) + " ms");
    }
}