package pcd.ass01.Jmultithread;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoidsPanel extends JPanel {
    private final BoidsModel model;

    public BoidsPanel(BoidsModel model) {
        this.model = model;
    }

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