package pcd.ass01.executor;

import javax.swing.*;
import java.awt.*;

public class BoidsPanel extends JPanel {
    private final BoidsModel model;

    public BoidsPanel(BoidsModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized (model) {
            for (Boid boid : model.getBoids()) {
                g.fillOval((int) boid.getPosition().getX(), (int) boid.getPosition().getY(), 5, 5);
            }
        }
    }
}