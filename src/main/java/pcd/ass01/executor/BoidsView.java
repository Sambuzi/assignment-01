package pcd.ass01.executor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;
/**
 * BoidsView is a class that represents the graphical user interface for the boids simulation.
 * It contains buttons, sliders, and a panel to display the boids.
 */
public class BoidsView extends JFrame {
    private final BoidsPanel panel;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final JTextField boidCountField;
    private final JSlider separationSlider;
    private final JSlider alignmentSlider;
    private final JSlider cohesionSlider;
    /**
     * Constructor for the BoidsView class.
     * @param model The BoidsModel instance that contains the boids data.
     * @param startListener ActionListener for the start button.
     * @param pauseListener ActionListener for the pause/resume button.
     * @param stopListener ActionListener for the stop button.
     */
    public BoidsView(BoidsModel model, ActionListener startListener, ActionListener pauseListener, ActionListener stopListener) {
        setTitle("Boids Simulation - Execution Version");
        setSize(800, 600); // Altezza regolata per un layout più compatto
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new BoidsPanel(model);
        add(panel, BorderLayout.CENTER);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause/Resume");
        stopButton = new JButton("Stop");
        boidCountField = new JTextField(5);
        boidCountField.setText("500");

        buttonPanel.add(new JLabel("Boids:"));
        buttonPanel.add(boidCountField);
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3, 2, 5, 5));
        sliderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        separationSlider = createSliderWithLabels(1.0);
        sliderPanel.add(new JLabel("Separation:"));
        sliderPanel.add(separationSlider);

        alignmentSlider = createSliderWithLabels(0.5);
        sliderPanel.add(new JLabel("Alignment:"));
        sliderPanel.add(alignmentSlider);

        cohesionSlider = createSliderWithLabels(0.5);
        sliderPanel.add(new JLabel("Cohesion:"));
        sliderPanel.add(cohesionSlider);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(sliderPanel, BorderLayout.SOUTH);

        add(controlPanel, BorderLayout.SOUTH);

        startButton.addActionListener(startListener);
        pauseButton.addActionListener(pauseListener);
        stopButton.addActionListener(stopListener);

        setVisible(true);
    }
    /**
     * Creates a slider with labels for the weights of the boids' behaviors.
     * @param initialValue The initial value of the slider.
     * @return A JSlider with labels.
     */
    private JSlider createSliderWithLabels(double initialValue) {
        JSlider slider = new JSlider(0, 200, (int) (initialValue * 100));
        slider.setPaintTicks(false);
        slider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(100, new JLabel("1"));
        labelTable.put(200, new JLabel("2"));
        slider.setLabelTable(labelTable);

        slider.setPreferredSize(new Dimension(150, 40));
        return slider;
    }
    /**
     * Sets the action command for the boids count.
     * @param command the boids count.
     */
    public int getBoidCount() {
        try {
            int count = Integer.parseInt(boidCountField.getText());
            if (count <= 0) {
                throw new NumberFormatException("Il numero di boid deve essere maggiore di 0.");
            }
            return count;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci un numero valido di boid (maggiore di 0).", "Errore", JOptionPane.ERROR_MESSAGE);
            boidCountField.setText("500");
            return 500;
        }
    }
    /**
     * Returns the value of the separation weight.
     * @return The separation weight as a double.
     */
    public double getSeparationWeight() {
        return separationSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }
    /**
     * Returns the value of the alignment weight.
     * @return The alignment weight as a double.
     */
    public double getAlignmentWeight() {
        return alignmentSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }
    /**
     * Returns the value of the cohesion weight.
     * @return The cohesion weight as a double.
     */
    public double getCohesionWeight() {
        return cohesionSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }
    /**
     * Returns the boids panel.
     * @return The BoidsPanel instance.
     */
    public void updateView() {
        panel.repaint();
    }
    /**
     * Sets the boid count in the text field.
     * @param numBoids The number of boids to set.
     */
    public void setBoidCount(int numBoids) {
        boidCountField.setText(String.valueOf(numBoids));
    }
}