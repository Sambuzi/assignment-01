package pcd.ass01.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * BoidsModel is a class that represents the model of the boids simulation.
 * It contains a list of boids and provides methods to manipulate them.
 */
public class BoidsModel {
    private final List<Boid> boids;
    /**
     * Constructor for the BoidsModel class.
     * Initializes an empty list of boids.
     */
    public BoidsModel() {
        this.boids = Collections.synchronizedList(new ArrayList<>());
    }
    /**
     * Returns the list of boids in the model.
     * @return The list of boids.
     */
    public List<Boid> getBoids() {
        return boids;
    }
    /**
     * Returns the number of boids in the model.
     * @return The number of boids.
     */
    public void addBoid(Boid boid) {
        boids.add(boid);
    }
    /**
     * Adds a boid to the model.
     * @param boid The boid to add.
     */
    public void clearBoids() {
        boids.clear();
    }
}