package pcd.ass01.Jmultithread;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;
/**
 * This class represents the graphical user interface (GUI) for the boids simulation.
 * It provides controls for starting, pausing, and stopping the simulation, as well as
 * adjusting various parameters related to boid behavior.
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

    public BoidsView(BoidsModel model, ActionListener startListener, ActionListener pauseListener, ActionListener stopListener) {
        setTitle("Boids Simulation - for jpf verification Version");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new BoidsPanel(model);
        add(panel, BorderLayout.CENTER);

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
     * Creates a JSlider with custom labels for the specified initial value.
     * The slider range is from 0 to 200, representing values from 0.0 to 2.0.
     *
     * @param initialValue the initial value for the slider (between 0.0 and 2.0)
     * @return a JSlider with custom labels
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
     * Returns the current value of the separation weight from the slider.
     * The value is scaled from 0.0 to 2.0.
     *
     * @return the separation weight
     */
    public double getSeparationWeight() {
        return separationSlider.getValue() / 100.0;
    }
    /**
     * Returns the current value of the alignment weight from the slider.
     * The value is scaled from 0.0 to 2.0.
     *
     * @return the alignment weight
     */
    public double getAlignmentWeight() {
        return alignmentSlider.getValue() / 100.0;
    }
    /**
     * Returns the current value of the cohesion weight from the slider.
     * The value is scaled from 0.0 to 2.0.
     *
     * @return the cohesion weight
     */
    public double getCohesionWeight() {
        return cohesionSlider.getValue() / 100.0;
    }

    public void updateView() {
        panel.repaint();
    }
}