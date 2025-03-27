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

    private final JSlider separationSlider;
    private final JSlider alignmentSlider;
    private final JSlider cohesionSlider;

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

        // Slider Panel
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3, 2));

        separationSlider = new JSlider(0, 200, 100); // Valore iniziale 1.0 (100%)
        alignmentSlider = new JSlider(0, 200, 50);  // Valore iniziale 0.5 (50%)
        cohesionSlider = new JSlider(0, 200, 50);   // Valore iniziale 0.5 (50%)

        sliderPanel.add(new JLabel("Separation Weight:"));
        sliderPanel.add(separationSlider);
        sliderPanel.add(new JLabel("Alignment Weight:"));
        sliderPanel.add(alignmentSlider);
        sliderPanel.add(new JLabel("Cohesion Weight:"));
        sliderPanel.add(cohesionSlider);

        add(sliderPanel, BorderLayout.NORTH);

        startButton.addActionListener(startListener);
        pauseButton.addActionListener(pauseListener);
        stopButton.addActionListener(stopListener);

        setVisible(true);
    }

    public int getBoidCount() {
        try {
            int count = Integer.parseInt(boidCountField.getText());
            if (count <= 0) {
                throw new NumberFormatException("Il numero di boid deve essere maggiore di 0.");
            }
            return count;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci un numero valido di boid (maggiore di 0).", "Errore", JOptionPane.ERROR_MESSAGE);
            boidCountField.setText("500"); // Ripristina il valore predefinito
            return 500; // Valore predefinito
        }
    }

    public double getSeparationWeight() {
        return separationSlider.getValue() / 100.0; // Converti da percentuale a valore decimale
    }

    public double getAlignmentWeight() {
        return alignmentSlider.getValue() / 100.0; // Converti da percentuale a valore decimale
    }

    public double getCohesionWeight() {
        return cohesionSlider.getValue() / 100.0; // Converti da percentuale a valore decimale
    }

    public void updateView() {
        panel.repaint();
    }
}