package pcd.ass01.multithread;

import java.util.List;
/**
 * Represents a single boid in the simulation.
 */
public class Boid {
    private static final double MAX_SPEED = 1.0;
    private static final double SEPARATION_DISTANCE = 20.0;
    private static final double ALIGNMENT_DISTANCE = 60.0; 
    private static final double COHESION_DISTANCE = 60.0; 

    private P2d position;
    private V2d velocity;
    /**
     * Constructor to initialize a boid with a position and velocity.
     *
     * @param position The initial position of the boid.
     * @param velocity The initial velocity of the boid.
     */
    public Boid(P2d position, V2d velocity) {
        this.position = position;
        this.velocity = velocity;
    }
    /**
     * Updates the velocity of the boid based on the positions and velocities of other boids.
     *
     * @param model The model containing all boids.
     * @param separationWeight Weight for separation behavior.
     * @param alignmentWeight Weight for alignment behavior.
     * @param cohesionWeight Weight for cohesion behavior.
     */
    public void updateVelocity(BoidsModel model, double separationWeight, double alignmentWeight, double cohesionWeight) {
        List<Boid> boids = model.getBoids();

        V2d separation = computeSeparation(boids);
        V2d alignment = computeAlignment(boids);
        V2d cohesion = computeCohesion(boids);

        velocity = velocity.sum(separation.mul(separationWeight))
                           .sum(alignment.mul(alignmentWeight))
                           .sum(cohesion.mul(cohesionWeight));

        limitVelocity();
    }
    /**
     * Updates the position of the boid based on its velocity and the screen dimensions.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    public void updatePos(int width, int height) {
        position = position.sum(velocity);

        double x = position.getX();
        double y = position.getY();

        if (x < 0) {
            x = 0;
            velocity = new V2d(-velocity.getX(), velocity.getY());
        } else if (x > width) {
            x = width;
            velocity = new V2d(-velocity.getX(), velocity.getY());
        }

        if (y < 0) {
            y = 0;
            velocity = new V2d(velocity.getX(), -velocity.getY());
        } else if (y > height) {
            y = height;
            velocity = new V2d(velocity.getX(), -velocity.getY());
        }

        position = new P2d(x, y);
    }
    /**
     * Sets the position of the boid.
     *
     * @param position The new position of the boid.
     */
    private void limitVelocity() {
        double speed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY());
        if (speed > MAX_SPEED) {
            velocity = velocity.normalize().mul(MAX_SPEED);
        }
    }
    /**
     * Computes the separation vector to avoid crowding other boids.
     *
     * @param boids The list of all boids in the simulation.
     * @return The separation vector.
     */
    private V2d computeSeparation(List<Boid> boids) {
        V2d separation = new V2d(0, 0);
        for (Boid other : boids) {
            if (other != this) {
                double distance = position.distanceTo(other.getPosition());
                if (distance < SEPARATION_DISTANCE) {
                    V2d diff = position.sub(other.getPosition());
                    separation = separation.sum(diff.normalize().mul(1.0 / distance));
                }
            }
        }
        return separation;
    }
    /**
     * Computes the alignment vector to match the velocity of nearby boids.
     *
     * @param boids The list of all boids in the simulation.
     * @return The alignment vector.
     */
    private V2d computeAlignment(List<Boid> boids) {
        V2d alignment = new V2d(0, 0);
        int count = 0;
        for (Boid other : boids) {
            if (other != this) {
                double distance = position.distanceTo(other.getPosition());
                if (distance < ALIGNMENT_DISTANCE) {
                    alignment = alignment.sum(other.getVelocity());
                    count++;
                }
            }
        }
        return count > 0 ? alignment.mul(1.0 / count) : alignment;
    }
    /**
     * Computes the cohesion vector to steer towards the average position of nearby boids.
     *
     * @param boids The list of all boids in the simulation.
     * @return The cohesion vector.
     */
    private V2d computeCohesion(List<Boid> boids) {
        P2d centerOfMass = new P2d(0, 0);
        int count = 0;
        for (Boid other : boids) {
            if (other != this) {
                double distance = position.distanceTo(other.getPosition());
                if (distance < COHESION_DISTANCE) {
                    centerOfMass = new P2d(centerOfMass.getX() + other.getPosition().getX(),
                                           centerOfMass.getY() + other.getPosition().getY());
                    count++;
                }
            }
        }
        if (count > 0) {
            centerOfMass = new P2d(centerOfMass.getX() / count, centerOfMass.getY() / count);
            return centerOfMass.sub(position).normalize();
        }
        return new V2d(0, 0);
    }
    /**
     * Gets the position of the boid.
     *
     * @return The position of the boid.
     */
    public P2d getPosition() {
        return position;
    }
    /**
     * Sets the position of the boid.
     *
     * @param position The new position of the boid.
     */
    public V2d getVelocity() {
        return velocity;
    }
}