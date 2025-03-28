package pcd.ass01.executor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoidTask implements Runnable {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;
    private final ReentrantLock lock;
    private final Condition pauseCondition;
    private final BoidsExecutorSimulator simulator;

    public BoidTask(Boid boid, BoidsModel model, BoidsView view, ReentrantLock lock, Condition pauseCondition, BoidsExecutorSimulator simulator) {
        this.boid = boid;
        this.model = model;
        this.view = view;
        this.lock = lock;
        this.pauseCondition = pauseCondition;
        this.simulator = simulator;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Rispetta lo stato di pausa
                lock.lock();
                try {
                    while (simulator.isPaused()) {
                        pauseCondition.await(); // Metti in attesa il thread
                    }
                } finally {
                    lock.unlock();
                }

                // Aggiorna velocità e posizione del boid
                boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                boid.updatePos(800, 600);

                Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}