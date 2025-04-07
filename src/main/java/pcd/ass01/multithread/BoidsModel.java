package pcd.ass01.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents the model of the boids simulation.
 * This class manages the list of boids and provides methods to manipulate them.
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
     * This method is synchronized to ensure thread safety when accessing the list of boids.
     *
     * @return A synchronized list of boids.
     */
    public synchronized List<Boid> getBoids() {
        return boids;
    }
    /**
     * Adds a boid to the list of boids in a synchronized manner.
     * This method is synchronized to ensure thread safety when modifying the list of boids.
     *
     * @param boid The boid to be added to the list.
     */
    public synchronized void addBoid(Boid boid) {
        boids.add(boid);
    }
}