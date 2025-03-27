package pcd.ass01.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoidsModel {
    private final List<Boid> boids;

    public BoidsModel() {
        this.boids = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized List<Boid> getBoids() {
        return new ArrayList<>(boids); // Ritorna una copia per evitare problemi di concorrenza
    }

    public synchronized void addBoid(Boid boid) {
        boids.add(boid);
    }
}