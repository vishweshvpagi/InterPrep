package javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuizApp extends JFrame {

    private JLabel lblQuestion;
    private JRadioButton[] radioButtons;
    private JButton btnNext;
    private ButtonGroup buttonGroup;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private int questionNumber = 1;
    static int score = 0; // Initialize score

    public QuizApp() {
        setTitle("InterPrep");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Center the window

        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("background.jpg"); // Change "background.jpg" to the path of your image file
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBackground(new Color(60, 63, 65));
        setContentPane(backgroundPanel);

        lblQuestion = new JLabel();
        lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Verdana", Font.BOLD, 18));
        lblQuestion.setForeground(new Color(187, 187, 188));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(lblQuestion, gbc);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        optionsPanel.setBackground(new Color(60, 63, 65));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(optionsPanel, gbc);

        radioButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = new JRadioButton();
            radioButtons[i].setFont(new Font("Verdana", Font.PLAIN, 16));
            radioButtons[i].setBackground(new Color(60, 63, 65));
            radioButtons[i].setForeground(new Color(187, 187, 188));
            optionsPanel.add(radioButtons[i]);
            buttonGroup.add(radioButtons[i]);
        }

        btnNext = new JButton("Next");
        btnNext.setFont(new Font("Verdana", Font.BOLD, 16));
        btnNext.setBackground(new Color(28, 134, 238));
        btnNext.setForeground(Color.WHITE);
        btnNext.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(btnNext, gbc);

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vishu", "root", "2005");
            preparedStatement = connection.prepareStatement("SELECT question_text, option1, option2, option3, option4, correct_answer FROM questions");
            resultSet = preparedStatement.executeQuery();
            showNextQuestion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void showNextQuestion() {
        try {
            if (resultSet.next()) {
                String question = resultSet.getString("question_text");
                lblQuestion.setText("Question " + questionNumber++ + ": " + question);

                for (int i = 0; i < radioButtons.length; i++) {
                    radioButtons[i].setText(resultSet.getString("option" + (i + 1)));
                    radioButtons[i].setSelected(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score); // Display score at the end
                dispose();
                executeScore(); // Execute Score class after quiz is completed
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve questions.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void checkAnswer() {
        String selectedAnswer = "";
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                selectedAnswer = radioButtons[i].getText();
                break;
            }
        }

        try {
            String correctAnswer = resultSet.getString("correct_answer");
            if (selectedAnswer.equalsIgnoreCase(correctAnswer)) {
                score++; // Increment score if the answer is correct
            }
            showNextQuestion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve answer.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
        buttonGroup.clearSelection();
    }

    private void executeScore() {
        String url = "jdbc:mysql://localhost:3306/vishu";
        String user = "root";
        String password = "2005";

        // Sample data: student name, quiz id, and marks
        String studentName = "vishu";
        int quizId = 1;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // SQL statement to insert marks into the database
            String sql = "INSERT INTO quiz_marks (student_name, quiz_id, marks) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, studentName);
                preparedStatement.setInt(2, quizId);
                preparedStatement.setInt(3, score);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Marks inserted successfully.");
                } else {
                    System.out.println("Failed to insert marks.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp quizApp = new QuizApp();
            quizApp.setVisible(true);
        });
    }
}
