package pcd.ass01.multithread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the main simulator for the boids simulation.
 * It manages the model, view, and threads for each boid.
 */
public class BoidsSimulator {
    private final BoidsModel model; 
    private final List<BoidThread> threads;
    private final BoidsView view;
    private boolean isPaused = false;
    private boolean isRunning = false;
    /**
     * Constructor to initialize the BoidsSimulator with a model.
     *
     * @param model The BoidsModel that contains the boids to be simulated.
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
     * Returns the view of the simulator.
     *
     * @return The BoidsView associated with this simulator.
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
     * If the simulation is running, it pauses it and shows a message dialog.
     * If the simulation is paused, it resumes it and shows a message dialog.
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
     * Stops the simulation and clears the boids from the model.
     * This method is synchronized to ensure thread safety when modifying the list of boids.
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
            model.getBoids().clear(); // Questo non funzionava prima!
        }
    
        view.updateView();
        isRunning = false;
        isPaused = false;
    }
    /**
     * Returns the view of the simulator.
     *
     * @return The BoidsView associated with this simulator.
     */
    public BoidsView getView() {
        return view;
    }
}