package pcd.ass01.multithread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BoidsView extends JFrame {
    private final BoidsPanel panel;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final JTextField boidCountField;

    public BoidsView(BoidsModel model, ActionListener startListener, ActionListener pauseListener, ActionListener stopListener) {
        setTitle("Boids Simulation - Multithread Version");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new BoidsPanel(model);
        add(panel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause/Resume");
        stopButton = new JButton("Stop");
        boidCountField = new JTextField(5);
        boidCountField.setText("500");

        controlPanel.add(new JLabel("Boids:"));
        controlPanel.add(boidCountField);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);

        startButton.addActionListener(startListener);
        pauseButton.addActionListener(pauseListener);
        stopButton.addActionListener(stopListener);

        setVisible(true);
    }

    public int getBoidCount() {
        try {
            return Integer.parseInt(boidCountField.getText());
        } catch (NumberFormatException e) {
            return 50;
        }
    }

    public void updateView() {
        panel.repaint();
    }
}