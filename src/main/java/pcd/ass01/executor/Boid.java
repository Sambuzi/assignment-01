package pcd.ass01.executor;

import java.util.List;

public class Boid {
    private static final double MAX_SPEED = 1.0; // Velocità massima
    private static final double SEPARATION_DISTANCE = 20.0; // Distanza minima per separazione
    private static final double ALIGNMENT_DISTANCE = 60.0; // Distanza massima per allineamento
    private static final double COHESION_DISTANCE = 60.0; // Distanza massima per coesione

    private P2d position;
    private V2d velocity;

    public Boid(P2d position, V2d velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void updateVelocity(BoidsModel model, double separationWeight, double alignmentWeight, double cohesionWeight) {
        List<Boid> boids = model.getBoids();

        V2d separation = computeSeparation(boids);
        V2d alignment = computeAlignment(boids);
        V2d cohesion = computeCohesion(boids);

        velocity = velocity.sum(separation.mul(separationWeight))
                           .sum(alignment.mul(alignmentWeight))
                           .sum(cohesion.mul(cohesionWeight));

        limitVelocity(); // Limita la velocità

        // Debug: Stampa la nuova velocità
        System.out.println("Boid " + this + " updated velocity: " + velocity);
    }

    public void updatePos(int width, int height) {
        position = position.sum(velocity);

        // Controlla i limiti dello schermo
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

        // Debug: Stampa la nuova posizione
        System.out.println("Boid " + this + " updated position: " + position);
    }

    private void limitVelocity() {
        double speed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY());
        if (speed > MAX_SPEED) {
            velocity = velocity.normalize().mul(MAX_SPEED);
        }
    }

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

    public P2d getPosition() {
        return position;
    }

    public V2d getVelocity() {
        return velocity;
    }

    @Override
    public String toString() {
        return "Boid@" + Integer.toHexString(hashCode());
    }
}