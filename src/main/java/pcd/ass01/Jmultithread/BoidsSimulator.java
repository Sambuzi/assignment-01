package pcd.ass01.Jmultithread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the main controller for the boids simulation for jpf.
 * It manages the simulation state, including starting, pausing, and stopping the simulation.
 * It also creates and manages threads for each boid in the simulation.
 */
public class BoidsSimulator {
    private final BoidsModel model; 
    private final List<BoidThread> threads;
    private final BoidsView view;
    private boolean isPaused = false;
    private boolean isRunning = false;
    /**
     * Constructor to initialize the BoidsSimulator with a BoidsModel and a BoidsView.
     * @param model The model representing the boids.
     */ 
    public BoidsSimulator(BoidsModel model) {
        this.model = model;
        this.threads = new ArrayList<>();
        this.view = new BoidsView(model, 
            e -> startSimulation(), 
            e -> togglePause(),     
            e -> stopSimulation()   
        );
    }
    /**
     * Starts the boids simulation.
     */
    public void startSimulation() {
        if (isRunning) {
            return;
        }

        stopSimulation();

        int boidCount = view.getBoidCount();
        model.getBoids().clear();

        for (int i = 0; i < boidCount; i++) {
            Boid boid = new Boid(
                new P2d(Math.random() * 800, Math.random() * 600),
                new V2d(Math.random() - 0.5, Math.random() - 0.5) 
            );
            model.addBoid(boid);
        }

        for (Boid boid : model.getBoids()) {
            BoidThread thread = new BoidThread(boid, model, view);
            threads.add(thread);
            thread.start();
        }

        isRunning = true;

        new Timer(40, e -> {
            if (!isPaused) {
                view.updateView();
            }
        }).start();
    }
    /**
     * Toggles the pause state of the simulation.
     */
    public void togglePause() {
        if (!isRunning) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {
            for (BoidThread thread : threads) {
                thread.pauseThread();
            }
            JOptionPane.showMessageDialog(view, "Simulazione sospesa.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (BoidThread thread : threads) {
                thread.resumeThread();
            }
            JOptionPane.showMessageDialog(view, "Simulazione ripresa.", "Ripresa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Stops the boids simulation.
     */
    public void stopSimulation() {
        if (!isRunning) {
            return;
        }
    
        for (BoidThread thread : threads) {
            thread.stopThread();
        }
        threads.clear();
    
        synchronized (model) {
            model.getBoids().clear();
        }
    
        view.updateView();
        isRunning = false;
        isPaused = false;
    }
    
}