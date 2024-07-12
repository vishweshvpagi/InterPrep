package javaproject;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Score <score>");
            return;
        }

        int score = Integer.parseInt(args[0]); // Assuming the score is passed as the first argument

        String url = "jdbc:mysql://localhost:3306/vishu";
        String user = "root";
        String password = "2005";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // SQL statement to retrieve usernames from the database
            String sql = "SELECT name FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Loop through the result set to insert scores for each username
                while (resultSet.next()) {
                    String username = resultSet.getString("name");

                    // SQL statement to insert marks into the database
                    String insertSql = "INSERT INTO quiz_marks (student_name, marks) VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, username);
                        insertStatement.setInt(2, score);

                        int rowsAffected = insertStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Marks inserted successfully for user: " + username);
                        } else {
                            System.out.println("Failed to insert marks for user: " + username);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
