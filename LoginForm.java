import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    // Declare Swing components for Login form
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton cancelButton;
    Color color;
    Font font;

    public LoginForm() 
    {
        // Set up the JFrame
        setTitle("Login Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        
        // Set the border of the frame
        ((JComponent) getContentPane()).setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        // Background color
        Color color = new Color(173, 216, 230);
        getContentPane().setBackground(color);

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(150, 10, 100, 30);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(titleLabel);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 180, 30);
        add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 30);
        add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBackground(color);
        loginButton.setBounds(80, 180, 100, 30);
        add(loginButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(color);
        cancelButton.setBounds(220, 180, 100, 30);
        add(cancelButton);

        // Add ActionListener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if(username.equals("azan") || password.equals(123))
                {
                    JOptionPane.showMessageDialog(null,"Login is Successfully");
                   new  DashboardEmployee().setVisible(true);
                     dispose(); // Close the login form
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect");
                }
            }
        });

        // Add ActionListener to the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });
    }

    // Method to check login credentials
    private void checkLogin(String username, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Login_e";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Create the SQL query
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                // Open the Employee Management System
                // new EmployeeManagementSystem().setVisible(true);
                // dispose(); // Close the login form
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
