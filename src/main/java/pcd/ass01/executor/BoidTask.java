package pcd.ass01.executor;

public class BoidTask implements Runnable {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;

    public BoidTask(Boid boid, BoidsModel model, BoidsView view) {
        this.boid = boid;
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Debug: Stampa che il task è in esecuzione
                System.out.println("Running task for Boid: " + boid);

                synchronized (model) {
                    boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                    boid.updatePos(800, 600);
                }

                Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}