package pcd.ass01.Jmultithread;
/**
 * This class represents a thread responsible for managing a single boid's behavior.
 * Each thread updates the boid's velocity and position based on the simulation rules.
 */
public class BoidThread extends Thread {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;
    private volatile boolean running = true;
    private volatile boolean isPaused = false;
        /**
     * Constructor to initialize the BoidThread with a boid, model, and view.
     *
     * @param boid  The boid managed by this thread.
     * @param model The model containing all boids.
     * @param view  The view used to retrieve simulation parameters.
     */
    public BoidThread(Boid boid, BoidsModel model, BoidsView view) {
        this.boid = boid;
        this.model = model;
        this.view = view;
    }
    /**
     * The run method contains the main loop for the thread.
     * It updates the boid's velocity and position based on the simulation rules.
     */
    @Override
    public void run() {
        try {
            while (running) {
                synchronized (this) {
                    while (isPaused) {
                        wait();
                    }
                }

                synchronized (model) {
                    boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                    boid.updatePos(800, 600);
                }

                Thread.sleep(40);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    /**
     * Stops the thread by setting the running flag to false and interrupting the thread.
    */
    public void stopThread() {
        running = false;
        interrupt();
    }
    /**
     * Pauses the thread by setting the isPaused flag to true.
     */
    public synchronized void pauseThread() {
        isPaused = true;
    }
    /**
     * Resumes the thread by setting the isPaused flag to false and notifying the thread to continue.
     */
    public synchronized void resumeThread() {
        isPaused = false;
        notify();
    }
}