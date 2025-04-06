package pcd.ass01.Jmultithread;

public class BoidThread extends Thread {
    private final Boid boid;
    private final BoidsModel model;
    private final BoidsView view;
    private volatile boolean running = true; // Indica se il thread è in esecuzione
    private volatile boolean isPaused = false; // Indica se il thread è in pausa

    public BoidThread(Boid boid, BoidsModel model, BoidsView view) {
        this.boid = boid;
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        try {
            while (running) {
                synchronized (this) {
                    while (isPaused) {
                        wait(); // Sospende il thread finché non viene notificato
                    }
                }

                synchronized (model) {
                    boid.updateVelocity(model, view.getSeparationWeight(), view.getAlignmentWeight(), view.getCohesionWeight());
                    boid.updatePos(800, 600);
                }

                Thread.sleep(40); // Simula un framerate (25 FPS = 40ms)
            }
        } catch (InterruptedException e) {
            // Il thread è stato interrotto, esce dal ciclo
            Thread.currentThread().interrupt();
        }
    }

    public void stopThread() {
        running = false; // Imposta il flag per terminare il ciclo
        interrupt(); // Interrompe il thread se è in attesa
    }

    public synchronized void pauseThread() {
        isPaused = true; // Imposta il flag per mettere in pausa il thread
    }

    public synchronized void resumeThread() {
        isPaused = false; // Imposta il flag per riprendere il thread
        notify(); // Notifica il thread per riprendere l'esecuzione
    }
}