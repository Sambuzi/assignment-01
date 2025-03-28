package pcd.ass01.executor;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoidsExecutorSimulator {
    private final BoidsModel model;
    private final BoidsView view;
    private ExecutorService executorService;
    private boolean isPaused = false;
    private boolean isRunning = false;

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

        // Timer per aggiornare la GUI periodicamente
        new Timer(32, e -> { // 32 ms = ~30 FPS
            if (!isPaused) {
                view.updateView();
            }
        }).start();

        // Esegui un task per ogni boid
        for (Boid boid : model.getBoids()) {
            executorService.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        synchronized (this) {
                            while (isPaused) {
                                wait(); // Attendi finché non viene ripreso
                            }
                        }

                        boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                        boid.updatePos(800, 600);

                        Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public synchronized void togglePause() {
        if (!isRunning) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {
            JOptionPane.showMessageDialog(view, "Simulazione sospesa.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Simulazione ripresa.", "Ripresa", JOptionPane.INFORMATION_MESSAGE);
            notifyAll(); // Riprendi tutti i thread in attesa
        }
    }

    public void stopSimulation() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        model.clearBoids();
        isRunning = false;
        isPaused = false;

        view.updateView();
    }
}