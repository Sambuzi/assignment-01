package pcd.ass01.multithread;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * This class represents the graphical panel for displaying the boids simulation.
 * It extends JPanel and overrides the paintComponent method to draw the boids on the panel.
 */
public class BoidsPanel extends JPanel {
    private final BoidsModel model;
    /**
     * Constructor to initialize the BoidsPanel with a BoidsModel.
     *
     * @param model The BoidsModel that contains the boids to be displayed.
     */
    public BoidsPanel(BoidsModel model) {
        this.model = model;
    }
    /**
     * This method is called whenever the panel needs to be repainted.
     * It clears the panel and draws the boids on it.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);

        List<Boid> boids = model.getBoids();
        for (Boid boid : boids) {
            P2d position = boid.getPosition();
            g2d.fillOval((int) position.getX(), (int) position.getY(), 5, 5); // Disegna un cerchio per ogni boid
        }
    }
}