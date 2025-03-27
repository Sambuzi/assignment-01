package pcd.ass01.multithread;

public class BoidThread extends Thread {
    private final Boid boid;
    private final BoidsModel model;
    private volatile boolean running = true;
    private final BoidsView view;

    public BoidThread(Boid boid, BoidsModel model, BoidsView view) {
        this.boid = boid;
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (model) {
                boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                boid.updatePos(800, 600); // Passa i limiti dello schermo
            }
            try {
                Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}