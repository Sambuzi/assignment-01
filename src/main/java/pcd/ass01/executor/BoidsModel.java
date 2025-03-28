package pcd.ass01.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoidsModel {
    private final List<Boid> boids;

    public BoidsModel() {
        this.boids = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Boid> getBoids() {
        return boids;
    }

    public void addBoid(Boid boid) {
        boids.add(boid);
    }

    public void clearBoids() {
        boids.clear(); // Ripulisce la lista dei boid
    }
}