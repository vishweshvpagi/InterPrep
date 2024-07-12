package javaproject;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowMarks extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea marksArea;

    public ShowMarks() {
        setTitle("Show Marks");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        marksArea = new JTextArea();
        marksArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        marksArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(marksArea);
        add(scrollPane, BorderLayout.CENTER);

        showMarks();
    }

    private void showMarks() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/vishu"; // Replace with your database name
        String username = "root"; // Replace with your database username
        String password = "2005"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM quiz_marks")) {

            StringBuilder marksBuilder = new StringBuilder();
            marksBuilder.append("ID\tStudent Name\tQuiz ID\tMarks\tTimestamp\n");
            marksBuilder.append("---------------------------------------------------------------\n");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                int quizId = resultSet.getInt("quiz_id");
                double marks = resultSet.getDouble("marks");
                String timestamp = resultSet.getString("timestamp");

                marksBuilder.append(id).append("\t")
                             .append(studentName).append("\t")
                             .append(quizId).append("\t")
                             .append(marks).append("\t")
                             .append(timestamp).append("\n");
            }

            marksArea.setText(marksBuilder.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
