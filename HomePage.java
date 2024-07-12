package javaproject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("InterPrep");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#2C3E50"));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel titleLabel = new JLabel("Java Coaching Application");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 25, 25, 25);

        gbc.gridx = 0;
        bodyPanel.add(createBlock("Mentoring", Color.decode("#2980B9")), gbc);
        gbc.gridx = 1;
        bodyPanel.add(createBlock("Access Resources", Color.decode("#C0392B")), gbc);
        gbc.gridx = 2;
        bodyPanel.add(createBlock("View Results", Color.decode("#27AE60")), gbc);

        getContentPane().add(bodyPanel, BorderLayout.CENTER);
    }

    private JPanel createBlock(String label, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(220, 100));
        panel.setBackground(color);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setBorder(new RoundedBorder(10));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonClick(label);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(color.darker().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(color);
            }
        });

        return panel;
    }

    private void handleButtonClick(String label) {
        switch (label) {
            case "Mentoring":
                openMentoringApp();
                break;
            case "Access Resources":
                openAccessResources();
                break;
            case "View Results":
                openViewResults();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Clicked on: " + label);
                break;
        }
    }

    private void openMentoringApp() {
        try {
            // Launch Mentoring App by invoking its main method
            QuizApp.main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to open Mentoring App.");
        }
    }

    private void openAccessResources() {
        SwingUtilities.invokeLater(() -> {
            Resources resourcesApp = new Resources();
            resourcesApp.setVisible(true);
        });
    }

    private void openViewResults() {
        SwingUtilities.invokeLater(() -> {
            ShowMarks showMarksApp = new ShowMarks();
            showMarksApp.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}

class RoundedBorder implements Border {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
