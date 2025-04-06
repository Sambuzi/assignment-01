package pcd.ass01.Jmultithread;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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
        setTitle("Boids Simulation - Virtual Thread Version");
        setSize(800, 600); // Altezza regolata per un layout più compatto
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new BoidsPanel(model);
        add(panel, BorderLayout.CENTER);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Margini ridotti

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

        // Pannello per gli slider
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3, 2, 5, 5)); // Griglia con due colonne e spaziatura ridotta
        sliderPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Margini ridotti

        // Slider per separazione
        separationSlider = createSliderWithLabels(1.0); // Valore iniziale 1.0
        sliderPanel.add(new JLabel("Separation:"));
        sliderPanel.add(separationSlider);

        // Slider per allineamento
        alignmentSlider = createSliderWithLabels(0.5); // Valore iniziale 0.5
        sliderPanel.add(new JLabel("Alignment:"));
        sliderPanel.add(alignmentSlider);

        // Slider per coesione
        cohesionSlider = createSliderWithLabels(0.5); // Valore iniziale 0.5
        sliderPanel.add(new JLabel("Cohesion:"));
        sliderPanel.add(cohesionSlider);

        // Pannello principale per controlli
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(sliderPanel, BorderLayout.SOUTH);

        add(controlPanel, BorderLayout.SOUTH);

        // Assegna i listener ai pulsanti
        startButton.addActionListener(startListener);
        pauseButton.addActionListener(pauseListener);
        stopButton.addActionListener(stopListener);

        setVisible(true);
    }

    private JSlider createSliderWithLabels(double initialValue) {
        // Slider con range da 0 a 2
        JSlider slider = new JSlider(0, 200, (int) (initialValue * 100)); // Scala da 0 a 200 per rappresentare 0.0 a 2.0
        slider.setPaintTicks(false);      // Non mostra i tick
        slider.setPaintLabels(true);      // Mostra solo le etichette principali

        // Etichette personalizzate per i valori principali
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(100, new JLabel("1"));
        labelTable.put(200, new JLabel("2"));
        slider.setLabelTable(labelTable);

        // Imposta dimensioni compatte
        slider.setPreferredSize(new Dimension(150, 40)); // Larghezza 150px, altezza 40px
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
            boidCountField.setText("500"); // Ripristina il valore predefinito
            return 500; // Valore predefinito
        }
    }

    public double getSeparationWeight() {
        return separationSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }

    public double getAlignmentWeight() {
        return alignmentSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }

    public double getCohesionWeight() {
        return cohesionSlider.getValue() / 100.0; // Converti da scala 0-200 a 0.0-2.0
    }

    public void updateView() {
        panel.repaint();
    }
}