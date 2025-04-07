package pcd.ass01.virtualthread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Monitors the state of the boids and manages their execution.
 * This class is responsible for pausing and resuming the simulation.
 */
public class BoidMonitor {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pauseCondition = lock.newCondition();
    private boolean isPaused = false;

    /**
     * Waits if the simulation is paused.
     * This method will block until the simulation is resumed.
     *
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public void waitIfPaused() throws InterruptedException {
        lock.lock();
        try {
            while (isPaused) {
                pauseCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }
    /**
     * Pauses the simulation. This method will block all threads until resumed.
     */ 
    public void togglePause() {
        lock.lock();
        try {
            isPaused = !isPaused;
            if (!isPaused) {
                pauseCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}