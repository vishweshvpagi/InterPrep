package javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Resources extends JFrame {
    public Resources() {
        setTitle("Resources");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#ecf0f1"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton resourcesButton = createButton("Online Resources", "#2980b9");
        resourcesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOnlineResources();
            }
        });
        panel.add(resourcesButton, gbc);

        gbc.gridy++;
        JButton videosButton = createButton("YouTube Videos", "#e74c3c");
        videosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openYouTubeVideos();
            }
        });
        panel.add(videosButton, gbc);

        add(panel);
    }

    private JButton createButton(String text, String colorHex) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode(colorHex));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode(colorHex).darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    private void openOnlineResources() {
        String url = "https://www.geeksforgeeks.org/java/";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening online resources: " + e.getMessage());
        }
    }

    private void openYouTubeVideos() {
        String url = "https://youtu.be/bm0OyhwFDuY?si=svciTRMXk13MrIoD";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening YouTube videos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Resources().setVisible(true);
            }
        });
    }
}
