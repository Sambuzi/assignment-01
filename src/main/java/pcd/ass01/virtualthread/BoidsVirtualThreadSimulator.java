package pcd.ass01.virtualthread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Simulates the behavior of boids using virtual threads.
 * This class manages the simulation, including starting, pausing, and stopping it.
 */ 
public class BoidsVirtualThreadSimulator {
    private final BoidsModel model;
    private final BoidsView view;
    private final List<Thread> virtualThreads = new ArrayList<>();
    private final BoidMonitor monitor = new BoidMonitor();
    private boolean isRunning = false;
    /**
     * Constructor to initialize the BoidsVirtualThreadSimulator with a given BoidsModel.
     *
     * @param model The BoidsModel to be used for the simulation.
     */
    public BoidsVirtualThreadSimulator(BoidsModel model) {
        this.model = model;
        this.view = new BoidsView(model,
            e -> startSimulation(),
            e -> togglePause(),
            e -> stopSimulation()
        );
    }
    /**
     * Starts the simulation by creating and starting virtual threads for each boid.
     * It also sets up a timer to update the GUI periodically.
     */
    public void startSimulation() {
        if (isRunning) {
            return;
        }

        stopSimulation();

        int boidCount = view.getBoidCount();
        model.clearBoids();

        for (int i = 0; i < boidCount; i++) {
            Boid boid = new Boid(
                new P2d(Math.random() * 800, Math.random() * 600),
                new V2d(Math.random() - 0.5, Math.random() - 0.5)
            );
            model.addBoid(boid);
        }

        isRunning = true;

        new Timer(32, e -> { // 32 ms = ~30 FPS
            view.updateView();
        }).start();

        for (Boid boid : model.getBoids()) {
            Thread virtualThread = Thread.ofVirtual().start(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        monitor.waitIfPaused();

                        boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                        boid.updatePos(800, 600);

                        Thread.sleep(40);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            virtualThreads.add(virtualThread);
        }
    }
    /**
     * Toggles the pause state of the simulation.
     * If the simulation is paused, it resumes; if it's running, it pauses.
     */ 
    public void togglePause() {
        monitor.togglePause();
    }
    /**
     * Stops the simulation by interrupting all virtual threads and clearing the model.
     */
    public void stopSimulation() {
        for (Thread thread : virtualThreads) {
            thread.interrupt();
        }
        virtualThreads.clear();
        model.clearBoids();
        isRunning = false;

        view.updateView();
    }
    public BoidsView getView() {
        return view;
    }
}