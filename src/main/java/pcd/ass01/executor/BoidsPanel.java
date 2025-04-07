package pcd.ass01.executor;

import javax.swing.*;
import java.awt.*;
/**
 * BoidsPanel is a class that represents the panel for the boids simulation.
 * It is responsible for rendering the boids on the screen.
 */
public class BoidsPanel extends JPanel {
    private final BoidsModel model;
    /**
     * Constructor for the BoidsPanel class.
     * @param model The BoidsModel instance that contains the boids data.
     */
    public BoidsPanel(BoidsModel model) {
        this.model = model;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        synchronized (model) {
            for (Boid boid : model.getBoids()) {
                P2d position = boid.getPosition();
                g.fillOval((int) position.getX(), (int) position.getY(), 5, 5);
            }
        }
    }
}