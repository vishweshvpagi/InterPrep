package javaproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserLogin extends JFrame {

    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnLogin;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserLogin frame = new UserLogin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UserLogin() {
        setTitle("Interprep");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);

        JPanel contentPane = new JPanel(new GridBagLayout());
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setForeground(Color.black);
        lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 46));
        contentPane.add(lblLogin, gbc);

        // Add "Hello" label under the login label
        gbc.gridy++;
   

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setForeground(Color.black);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblUsername, gbc);

        gbc.gridy++;
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textField.setColumns(20);
        contentPane.add(textField, gbc);

        gbc.gridy++;
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.black);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblPassword, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordField.setColumns(20);
        contentPane.add(passwordField, gbc);

        gbc.gridy++;
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnLogin.setBackground(new Color(0, 40, 100));
        btnLogin.setForeground(Color.white);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vishu", "root", "2005");
                    PreparedStatement st = connection.prepareStatement("SELECT name, password FROM student WHERE name=? AND password=?");
                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        HomePage homePage = new HomePage();
                        homePage.setTitle("Welcome");
                        homePage.setVisible(true);
                        JOptionPane.showMessageDialog(btnLogin, "You have successfully logged in", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(btnLogin, "Wrong Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        contentPane.add(btnLogin, gbc);

        gbc.gridy++;
       


    }
}
