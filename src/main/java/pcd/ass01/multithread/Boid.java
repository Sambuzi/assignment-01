package pcd.ass01.multithread;

import java.util.List;

public class Boid {
    private P2d position;
    private V2d velocity;

    public Boid(P2d position, V2d velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void updateVelocity(BoidsModel model) {
        List<Boid> boids = model.getBoids();
    
        V2d separation = computeSeparation(boids);
        V2d alignment = computeAlignment(boids);
        V2d cohesion = computeCohesion(boids);
    
        // Pesi ridotti per diminuire l'influenza dei vettori
        double separationWeight = 1.0;
        double alignmentWeight = 0.5;
        double cohesionWeight = 0.5;
    
        velocity = velocity.sum(separation.mul(separationWeight))
                           .sum(alignment.mul(alignmentWeight))
                           .sum(cohesion.mul(cohesionWeight));
    
        limitVelocity(); // Limita la velocità
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
    }

    private void limitVelocity() {
        double maxSpeed = 2.0; // Velocità massima
        double speed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY());
        if (speed > maxSpeed) {
            velocity = velocity.normalize().mul(maxSpeed);
        }
    }

    private V2d computeSeparation(List<Boid> boids) {
        V2d separation = new V2d(0, 0);
        for (Boid other : boids) {
            if (other != this) {
                double distance = position.distanceTo(other.getPosition());
                if (distance < 20) { // Distanza minima per separazione
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
                if (distance < 50) { // Distanza massima per allineamento
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
                if (distance < 50) { // Distanza massima per coesione
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
}