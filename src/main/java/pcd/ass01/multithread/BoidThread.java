package pcd.ass01.multithread;

/**
 * This class represents a thread responsible for managing a single boid's behavior.
 * Each thread updates the boid's velocity and position based on the simulation rules.
 */
public class BoidThread extends Thread {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;
    private volatile boolean running = true; // Indicates if the thread is running
    private volatile boolean isPaused = false; // Indicates if the thread is paused

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
     * The main execution loop for the thread.
     * Updates the boid's velocity and position, and pauses if required.
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

                Thread.sleep(40); // Simulates a frame rate (25 FPS = 40ms)
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
     * Pauses the thread by setting the paused flag to true.
     */
    public synchronized void pauseThread() {
        isPaused = true;
    }

    /**
     * Resumes the thread by setting the paused flag to false and notifying the thread.
     */
    public synchronized void resumeThread() {
        isPaused = false;
        notify();
    }
}