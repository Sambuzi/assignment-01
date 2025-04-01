package pcd.ass01.virtualthread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoidMonitor {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pauseCondition = lock.newCondition();
    private boolean isPaused = false;

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

    public void togglePause() {
        lock.lock();
        try {
            isPaused = !isPaused;
            if (!isPaused) {
                pauseCondition.signalAll(); // Risveglia tutti i thread in attesa
            }
        } finally {
            lock.unlock();
        }
    }
}