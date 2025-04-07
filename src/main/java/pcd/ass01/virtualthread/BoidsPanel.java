package pcd.ass01.virtualthread;

import javax.swing.*;
import java.awt.*;
/**
 * Represents the panel where the boids are drawn.
 * This class is responsible for rendering the boids on the screen.
 */ 
public class BoidsPanel extends JPanel {
    private final BoidsModel model;
    /**
     * Constructor to initialize the BoidsPanel with a given BoidsModel.
     *
     * @param model The BoidsModel to be used for rendering.
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
