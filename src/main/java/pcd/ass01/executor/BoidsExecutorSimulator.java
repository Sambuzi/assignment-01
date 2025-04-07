package pcd.ass01.executor;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * BoidsExecutorSimulator is a class that simulates the behavior of boids using an ExecutorService.
 * It manages the simulation, including starting, pausing, and stopping it.
 */
public class BoidsExecutorSimulator {
    private final BoidsModel model;
    private final BoidsView view;
    private ExecutorService executorService;
    private boolean isPaused = false;
    private boolean isRunning = false;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pauseCondition = lock.newCondition();
    /**
     * Constructor for the BoidsExecutorSimulator class.
     * @param model The BoidsModel instance that contains the boids data.
     */
    public BoidsExecutorSimulator(BoidsModel model) {
        this.model = model;
        this.view = new BoidsView(model,
            e -> startSimulation(),
            e -> togglePause(),
            e -> stopSimulation()
        );
    }
    /**
     * Starts the simulation by creating a number of boids and updating their positions.
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

        executorService = Executors.newFixedThreadPool(boidCount);
        isRunning = true;

        
        new Timer(32, e -> { // 32 ms = ~30 FPS
            if (!isPaused) {
                view.updateView();
            }
        }).start();

        for (Boid boid : model.getBoids()) {
            executorService.submit(new BoidTask(boid, model, view, lock, pauseCondition, this));
        }
    }
    /**
     * Toggles the pause state of the simulation.
     */
    public void togglePause() {
        lock.lock();
        try {
            isPaused = !isPaused;

            if (isPaused) {
                JOptionPane.showMessageDialog(view, "Simulazione sospesa.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Simulazione ripresa.", "Ripresa", JOptionPane.INFORMATION_MESSAGE);
                pauseCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
    /**
     * Stops the simulation and clears the boids.
     */
    public void stopSimulation() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        model.clearBoids();
        isRunning = false;
        isPaused = false;

        view.updateView();
    }
    /**
     * Checks if the simulation is running.
     * @return true if the simulation is running, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }
    /**
     * Checks if the simulation is running.
     * @return true if the simulation is running, false otherwise.
     */
    public BoidsView getView() {
        return view;
    }
}