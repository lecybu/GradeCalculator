import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

public class Grade_calculator extends JFrame implements ActionListener {
    private static JFormattedTextField milestone1Field, milestone2Field, terminalField;
    private static JTextField resultField;

    public Grade_calculator() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the UI components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add a margin of 20 pixels

        JLabel milestone1Label = new JLabel("Milestone 1:");
        milestone1Field = createTextField();
        JLabel milestone2Label = new JLabel("Milestone 2:");
        milestone2Field = createTextField();
        JLabel terminalLabel = new JLabel("Terminal Assessment:");
        terminalField = createTextField();
        JLabel resultLabel = new JLabel("Final Grade:");
        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setBackground(Color.WHITE);
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        // Add components to panel
        panel.add(milestone1Label);
        panel.add(milestone1Field);
        panel.add(milestone2Label);
        panel.add(milestone2Field);
        panel.add(terminalLabel);
        panel.add(terminalField);
        panel.add(resultLabel);
        panel.add(resultField);
        panel.add(new JLabel());
        panel.add(calculateButton);

        // Add panel to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JFormattedTextField createTextField() {
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(100);
        formatter.setAllowsInvalid(false);
        JFormattedTextField textField = new RoundedTextField(formatter, 15); // 15 is the radius for rounded corners
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setColumns(10);
        return textField;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Calculate")) {
            try {
                int milestone1 = Integer.parseInt(milestone1Field.getText());
                int milestone2 = Integer.parseInt(milestone2Field.getText());
                int terminal = Integer.parseInt(terminalField.getText());

                if (milestone1 > 25 || milestone1 < 1 ||
                        milestone2 > 40 || milestone2 < 1 ||
                        terminal > 35 || terminal < 1) {
                    throw new NumberFormatException();
                }

                double result = (milestone1 * 0.25) + (milestone2 * 0.40) + (terminal * 0.35);
                resultField.setText(Double.toString(result));
            } catch (NumberFormatException ex) {
                String errorMessage;
                if (milestone1Field.getText().isEmpty() || milestone2Field.getText().isEmpty() || terminalField.getText().isEmpty()) {
                    errorMessage = "Please enter your grade.";
                } else {
                    errorMessage = "Please enter correct values: Milestone 1: 1-25 / Milestone 2: 1-40 / Terminal Assessment: 1-35";
                }
                JOptionPane.showMessageDialog(this, errorMessage, "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Grade_calculator();
    }

    // Custom class for rounded text field
    class RoundedTextField extends JFormattedTextField {
        private int radius;

        public RoundedTextField(NumberFormatter formatter, int radius) {
            super(formatter);
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
}    
