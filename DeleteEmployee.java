import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class DeleteEmployee extends JFrame {

    // Declare Swing components for Employee form
    JTextField idField;
    JButton deleteButton;
    JButton backButton;
    Color color;
    Font font;

    public DeleteEmployee() {
        // Set up the JFrame
        setTitle("Delete Employee Requirement Form");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color color= new Color(173, 216, 230); // RGB for dark blue
        getContentPane().setBackground(color);

        JLabel l1 = new JLabel("Delete Employee Form");
        l1.setBounds(300, 10, 300, 50);
        l1.setFont(new Font("Serif", Font.BOLD, 30));
        l1.setForeground(Color.BLACK);
        add(l1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 900, 20);
        add(separator);

        // Initialize and add Swing components
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 70, 100, 30);
        idLabel.setForeground(Color.BLACK);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 70, 240, 30);
        add(idField);

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(color);
        deleteButton.setBounds(150, 130, 100, 30);
        add(deleteButton);

        backButton = new JButton("Back");
        backButton.setBackground(color);
        backButton.setBounds(290, 130, 100, 30);
        add(backButton);

        // Add ActionListener to the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        // Add ActionListener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the form and go back
            }
        });
    }

    // Method to delete employee data from the database
    private void deleteEmployee() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Add_e";
        String dbUsername = "yourUsername";
        String dbPassword = "yourPassword";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Create the SQL query
            String sql = "DELETE FROM employee1 WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idField.getText());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee deleted successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete employee.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            // Close the resources
            try {
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

    // Method to clear the input fields
    private void clearFields() {
        idField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeleteEmployee().setVisible(true);
            }
        });
    }
}
