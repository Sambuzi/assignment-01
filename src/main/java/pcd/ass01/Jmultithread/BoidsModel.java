package pcd.ass01.Jmultithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents the model of the boids simulation for jpf.
 * This class contains a list of boids and methods to manage them.
 */
public class BoidsModel {
    private final List<Boid> boids;
    /**
     * Constructor to initialize the BoidsModel with an empty list of boids.
     */
    public BoidsModel() {
        this.boids = Collections.synchronizedList(new ArrayList<>());
    }
    /**
     * Returns the list of boids in a synchronized manner.
     *
     * @return A synchronized list of boids.
     */ 
    public synchronized List<Boid> getBoids() {
        return boids;
    }
    /**
     * Adds a boid to the model in a synchronized manner.
     *
     * @param boid The boid to be added.
     */
    public synchronized void addBoid(Boid boid) {
        boids.add(boid);
    }
}