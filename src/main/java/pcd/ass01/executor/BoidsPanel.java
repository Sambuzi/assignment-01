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

        // Imposta il colore per i boid
        g.setColor(Color.BLUE);

        // Disegna ogni boid
        synchronized (model) {
            for (Boid boid : model.getBoids()) {
                P2d position = boid.getPosition();
                g.fillOval((int) position.getX(), (int) position.getY(), 5, 5);
            }
        }
    }
}