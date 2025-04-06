package pcd.ass01.Jmultithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoidsModel {
    private final List<Boid> boids;

    public BoidsModel() {
        this.boids = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized List<Boid> getBoids() {
        return boids; // Restituisci direttamente la lista sincronizzata
    }
    

    public synchronized void addBoid(Boid boid) {
        boids.add(boid);
    }
}