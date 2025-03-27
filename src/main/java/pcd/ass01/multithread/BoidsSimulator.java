package pcd.ass01.multithread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BoidsSimulator {
    private final BoidsModel model; 
    private final List<BoidThread> threads;
    private final BoidsView view;
    private boolean isPaused = false;

    public BoidsSimulator(BoidsModel model) {
        this.model = model;
        this.threads = new ArrayList<>();
        this.view = new BoidsView(model, 
            e -> startSimulation(), 
            e -> togglePause(),     
            e -> stopSimulation()   
        );
    }

    public void startSimulation() {
        stopSimulation(); // Ferma simulazioni precedenti

        int boidCount = view.getBoidCount(); // Numero di boid dalla GUI
        model.getBoids().clear(); // Pulisci il modello esistente

        for (int i = 0; i < boidCount; i++) {
            Boid boid = new Boid(
                new P2d(Math.random() * 800, Math.random() * 600), // Posizione casuale
                new V2d(Math.random() - 0.5, Math.random() - 0.5)  // Velocità casuale
            );
            model.addBoid(boid);
        }

        // Crea un thread per ogni boid
        for (Boid boid : model.getBoids()) {
            BoidThread thread = new BoidThread(boid, model);
            threads.add(thread);
            thread.start();
        }

        // Aggiorna la GUI periodicamente
        new Timer(40, e -> {
            if (!isPaused) {
                view.updateView();
            }
        }).start();
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void stopSimulation() {
        for (BoidThread thread : threads) {
            thread.stopThread();
        }
        threads.clear();
    }
}
