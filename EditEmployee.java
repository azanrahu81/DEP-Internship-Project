import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class EditEmployee extends JFrame {

    // Declare Swing components for Employee form
    JTextField idField;
    JTextField nameField;
    JTextField positionField;
    JTextField departmentField;
    JTextField salaryField;
    JButton updateButton;
    JButton backButton;
    Color color;
    Font font;

    public EditEmployee() {
        // Set up the JFrame
        setTitle("Edit Employee Requirement Form");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color darkBlue = new Color(173, 216, 230); // RGB for dark blue
        getContentPane().setBackground(darkBlue);

        JLabel l1 = new JLabel("Edit Employee Form");
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

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 30);
        nameLabel.setForeground(Color.BLACK);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 120, 330, 30);
        add(nameField);

        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setBounds(50, 170, 100, 30);
        positionLabel.setForeground(Color.BLACK);
        add(positionLabel);

        positionField = new JTextField();
        positionField.setBounds(150, 170, 330, 30);
        add(positionField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 220, 100, 30);
        departmentLabel.setForeground(Color.BLACK);
        add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setBounds(150, 220, 330, 30);
        add(departmentField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(50, 270, 100, 30);
        salaryLabel.setForeground(Color.BLACK);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(150, 270, 330, 30);
        add(salaryField);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 330, 100, 30);
        updateButton.setBackground(color);
        add(updateButton);

        backButton = new JButton("Back");
        backButton.setBackground(color);
        backButton.setBounds(290, 330, 100, 30);
        add(backButton);

        // Add ActionListener to the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
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

    // Method to update employee data in the database
    private void updateEmployee() {
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
            String sql = "UPDATE employee SET name = ?, position = ?, department = ?, salary = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, positionField.getText());
            preparedStatement.setString(3, departmentField.getText());
            preparedStatement.setString(4, salaryField.getText());
            preparedStatement.setString(5, idField.getText());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update employee.");
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
        nameField.setText("");
        positionField.setText("");
        departmentField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditEmployee().setVisible(true);
            }
        });
    }
}
