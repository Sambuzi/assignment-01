package pcd.ass01.virtualthread;

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
     * Constructor to initialize the BoidsModel with a given list of boids.
     *
     * @param boids The initial list of boids.
     */
    public List<Boid> getBoids() {
        return boids;
    }
    /**
     * Adds a boid to the model.
     *
     * @param boid The boid to be added.
     */ 
    public void addBoid(Boid boid) {
        boids.add(boid);
    }
    /**
     * Removes a boid from the model.
     *
     * @param boid The boid to be removed.
     */
    public void clearBoids() {
        boids.clear();
    }
}