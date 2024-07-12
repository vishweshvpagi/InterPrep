package javaproject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Main {
    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_management";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Restaurant Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Table to display data
            JTable table = new JTable();
            DefaultTableModel tableModel = new DefaultTableModel();
            table.setModel(tableModel);
            tableModel.addColumn("Menu ID");
            tableModel.addColumn("Restaurant ID");
            tableModel.addColumn("Item Name");
            tableModel.addColumn("Description");
            tableModel.addColumn("Price");

            // Load JDBC driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "MySQL JDBC Driver not found", "Driver Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fetch data from database
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM menu")) {

                while (rs.next()) {
                    int menuId = rs.getInt("menu_id");
                    int restaurantId = rs.getInt("restaurant_id");
                    String itemName = rs.getString("item_name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");

                    tableModel.addRow(new Object[]{menuId, restaurantId, itemName, description, price});
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error retrieving data from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}