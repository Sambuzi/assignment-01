package pcd.ass01.executor;

import java.util.ArrayList;
import java.util.List;

public class BoidsModel {
    private final List<Boid> boids;

    public BoidsModel() {
        this.boids = new ArrayList<>();
    }

    public synchronized List<Boid> getBoids() {
        return boids;
    }

    public synchronized void addBoid(Boid boid) {
        boids.add(boid);
    }
}