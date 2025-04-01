package pcd.ass01.virtualthread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BoidsVirtualThreadSimulator {
    private final BoidsModel model;
    private final BoidsView view;
    private final List<Thread> virtualThreads = new ArrayList<>();
    private final BoidMonitor monitor = new BoidMonitor();
    private boolean isRunning = false;

    public BoidsVirtualThreadSimulator(BoidsModel model) {
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

        isRunning = true;

        // Timer per aggiornare la GUI periodicamente
        new Timer(32, e -> { // 32 ms = ~30 FPS
            view.updateView();
        }).start();

        // Crea e avvia un Virtual Thread per ogni boid
        for (Boid boid : model.getBoids()) {
            Thread virtualThread = Thread.ofVirtual().start(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        monitor.waitIfPaused(); // Attendi se la simulazione è in pausa

                        boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                        boid.updatePos(800, 600);

                        Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            virtualThreads.add(virtualThread);
        }
    }

    public void togglePause() {
        monitor.togglePause();
    }

    public void stopSimulation() {
        for (Thread thread : virtualThreads) {
            thread.interrupt();
        }
        virtualThreads.clear();
        model.clearBoids();
        isRunning = false;

        view.updateView();
    }
}