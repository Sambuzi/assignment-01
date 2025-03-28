package pcd.ass01.executor;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoidsExecutorSimulator {
    private final BoidsModel model;
    private final BoidsView view;
    private ExecutorService executorService; // Pool di thread per i task
    private boolean isPaused = false;
    private boolean isRunning = false; // Stato della simulazione

    public BoidsExecutorSimulator(BoidsModel model) {
        this.model = model;
        this.view = new BoidsView(model,
            e -> startSimulation(),
            e -> togglePause(),
            e -> stopSimulation()
        );
    }

    public void startSimulation() {
        if (isRunning) {
            return; // Evita di avviare una nuova simulazione se è già in esecuzione
        }

        stopSimulation(); // Ferma eventuali simulazioni precedenti

        int boidCount = view.getBoidCount(); // Numero di boid dalla GUI
        model.getBoids().clear(); // Pulisci il modello esistente

        // Crea i boid
        for (int i = 0; i < boidCount; i++) {
            Boid boid = new Boid(
                new P2d(Math.random() * 800, Math.random() * 600), // Posizione casuale
                new V2d(Math.random() - 0.5, Math.random() - 0.5)  // Velocità casuale
            );
            model.addBoid(boid);
        }

        // Crea un nuovo pool di thread
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        isRunning = true;

        // Timer per aggiornare la GUI periodicamente
        new Timer(40, e -> {
            if (!isPaused) {
                view.updateView();
            }
        }).start();

        // Esegui i task per ogni boid
        for (Boid boid : model.getBoids()) {
            executorService.submit(new BoidTask(boid, model, view));
        }
    }

    public void togglePause() {
        if (!isRunning) {
            return; // Non fare nulla se la simulazione non è in esecuzione
        }

        isPaused = !isPaused;

        if (isPaused) {
            JOptionPane.showMessageDialog(view, "Simulazione sospesa.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Simulazione ripresa.", "Ripresa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void stopSimulation() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow(); // Arresta tutti i task
        }
        model.getBoids().clear(); // Ripristina il modello
        isRunning = false;
        isPaused = false;
        view.updateView(); // Aggiorna la GUI per riflettere lo stato iniziale
    }
}