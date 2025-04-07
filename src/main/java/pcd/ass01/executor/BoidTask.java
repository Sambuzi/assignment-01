package pcd.ass01.executor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * BoidTask is a class that represents a task for a single boid in the simulation.
 * It implements Runnable to allow it to be executed by an ExecutorService.
 */
public class BoidTask implements Runnable {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;
    private final ReentrantLock lock;
    private final Condition pauseCondition;
    private final BoidsExecutorSimulator simulator;
    /**
     * Constructor for the BoidTask class.
     * @param boid The boid to be simulated.
     * @param model The model containing the boids data.
     * @param view The view for rendering the simulation.
     * @param lock The lock for synchronizing access to shared resources.
     * @param pauseCondition The condition for pausing the simulation.
     * @param simulator The simulator managing the simulation state.
     */
    public BoidTask(Boid boid, BoidsModel model, BoidsView view, ReentrantLock lock, Condition pauseCondition, BoidsExecutorSimulator simulator) {
        this.boid = boid;
        this.model = model;
        this.view = view;
        this.lock = lock;
        this.pauseCondition = pauseCondition;
        this.simulator = simulator;
    }
    /**
     * The run method that contains the main logic for updating the boid's position and velocity.
     * It respects the pause state of the simulation.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    while (simulator.isPaused()) {
                        pauseCondition.await(); 
                    }
                } finally {
                    lock.unlock();
                }

                boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                boid.updatePos(800, 600);

                Thread.sleep(40);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}