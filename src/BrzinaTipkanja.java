import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class BrzinaTipkanja extends JFrame {

    final private JLabel originalTextLabel;
    final private JTextArea upisaniTextArea;
    final private JLabel rpmLabel;
    final private JLabel najboljiRpmLabel;
    final private JLabel vrijemeLabel;
    final private JButton restartButton;
    final private String[] originalTextovi = {
            "Java was developed by James Gosling at Sun Microsystems and released in 1995.",
            "Java is platform-independent, meaning it can run on any platform that supports Java.",
            "Java applications are compiled to bytecode, which can be executed by the Java Virtual Machine.",
            "Java is known for its \"write once, run anywhere\" (WORA) philosophy.",
            "Java is widely used in web development, enterprise applications, mobile development and more.",
            "Java has a strong ecosystem with a vast collection of libraries and frameworks.",
            "Java supports multithreading, allowing programs to perform multiple tasks simultaneously.",
            "Java has automatic memory management through garbage collection, which helps in memory optimization.",
            "Java is an object-oriented programming language, emphasizing modularity and reusability.",
            "Java continues to evolve with regular updates and improvements, maintaining its relevance in the software industry."
    };
    private String originalText;
    private int upisaniChar = 0;
    private long start;
    private int najboljiRpm = 0;

    public BrzinaTipkanja() {
        setTitle("Test brzine tipkanja");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        originalTextLabel = new JLabel();
        originalTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(originalTextLabel);

        upisaniTextArea = new JTextArea();
        upisaniTextArea.setLineWrap(true);
        upisaniTextArea.setEditable(true);

        upisaniTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (start == 0) {
                    start = System.currentTimeMillis();
                }

                if (upisaniChar < originalText.length()) {
                    if (originalText.charAt(upisaniChar) == e.getKeyChar()) {
                        upisaniChar++;
                        upisaniTextArea.setForeground(Color.GREEN);
                        upisaniTextArea.setText(originalText.substring(0, upisaniChar));
                    } else {
                        upisaniTextArea.setForeground(Color.RED);
                    }
                }

                if (upisaniChar == originalText.length()) {
                    long kraj = System.currentTimeMillis();
                    long vrijeme = kraj - start;
                    double seconds = vrijeme / 1000.0;
                    int rijeci = originalText.split("\\s+").length;
                    int rpm = (int) (rijeci / (seconds / 60.0));

                    if (rpm > najboljiRpm) {
                        najboljiRpm = rpm;
                        najboljiRpmLabel.setText("Najbolji RPM: " + najboljiRpm);
                    }

                    rpmLabel.setText("Tvoja brzina tipkanja: " + rpm + " RPM");
                    vrijemeLabel.setText("Vrijeme potrebno: " + seconds + " s");
                    rpmLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    vrijemeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        add(upisaniTextArea);

        rpmLabel = new JLabel();
        add(rpmLabel);

        vrijemeLabel = new JLabel();
        add(vrijemeLabel);

        najboljiRpmLabel = new JLabel("Najbolji RPM: 0");
        najboljiRpmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(najboljiRpmLabel);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            upisaniChar = 0;
            start = 0;
            originalText = getRandomOriginalText();
            originalTextLabel.setText(originalText);
            upisaniTextArea.setText("");
            rpmLabel.setText("");
            vrijemeLabel.setText("");
            upisaniTextArea.requestFocus();
        });
        add(restartButton);

        setVisible(true);
        restartButton.doClick();
    }
    private String getRandomOriginalText() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(originalTextovi.length);
        return originalTextovi[randomIndex];
    }
}





