package pcd.ass01.multithread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BoidsSimulator {
    private final BoidsModel model; 
    private final List<BoidThread> threads;
    private final BoidsView view;
    private boolean isPaused = false;
    private boolean isRunning = false; // Stato della simulazione

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
        if (isRunning) {
            return; // Evita di avviare una nuova simulazione se è già in esecuzione
        }

        stopSimulation(); // Ferma simulazioni precedenti e ripristina lo stato iniziale

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
            BoidThread thread = new BoidThread(boid, model, view);
            threads.add(thread);
            thread.start();
        }

        isRunning = true; // La simulazione è in esecuzione

        // Aggiorna la GUI periodicamente
        new Timer(40, e -> {
            if (!isPaused) {
                view.updateView();
            }
        }).start();
    }

    public void togglePause() {
        if (!isRunning) {
            return; // Non fare nulla se la simulazione non è in esecuzione
        }

        isPaused = !isPaused;

        if (isPaused) {
            for (BoidThread thread : threads) {
                thread.pauseThread(); // Metti in pausa ogni thread
            }
            JOptionPane.showMessageDialog(view, "Simulazione sospesa.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (BoidThread thread : threads) {
                thread.resumeThread(); // Riprendi ogni thread
            }
            JOptionPane.showMessageDialog(view, "Simulazione ripresa.", "Ripresa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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
    
}