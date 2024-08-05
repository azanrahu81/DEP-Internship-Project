import javax.swing.*;
import java.math.BigDecimal;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class EmployeeManagementSystem extends JFrame {

    // Declare Swing components for Employee form
    JTextField idField;
    JTextField nameField;
    JTextField positionField;
    JTextField departmentField;
    JTextField salaryField;
    JButton saveButton;
    JButton backButton;
    Color color;
    Font font;

    public EmployeeManagementSystem() {
        // Set up the JFrame
        setTitle("Employee Requirement Form");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color color = new Color(173, 216, 230); // RGB for dark blue
        getContentPane().setBackground(color);

        JLabel l1 = new JLabel("Add Employee Form");
        l1.setBounds(300, 10, 300, 30);
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
        idField.setBounds(150, 70, 330, 30);
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

        saveButton = new JButton("Save");
        saveButton.setBackground(color);
        saveButton.setBounds(150, 330, 100, 30);
        add(saveButton);

        backButton = new JButton("Next");
        backButton.setBackground(color);
        backButton.setBounds(290, 330, 100, 30);
        add(backButton);

        // Add ActionListener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        // Add ActionListener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new ViewAllEmployeesForm().setVisible(true);
                dispose(); // Close the form and go back
            }
        });
    }

    // Method to save employee data to the database
  // Method to save employee data to the database
private void saveEmployee() {
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
        String sql = "INSERT INTO employee1 (id, name, position, department, salary) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, idField.getText());
        preparedStatement.setString(2, nameField.getText());
        preparedStatement.setString(3, positionField.getText());
        preparedStatement.setString(4, departmentField.getText());

        // Validate and handle salary input
        String salaryText = salaryField.getText();
        if (salaryText == null || salaryText.trim().isEmpty()) {
            preparedStatement.setNull(5, Types.INTEGER); // Set NULL if empty
        } else {
            try {
                // Attempt to parse the salary text as an Integer
                int salary = Integer.parseInt(salaryText);
                preparedStatement.setInt(5, salary); // Set the Integer value
            } catch (NumberFormatException ex) {
                // Handle the case where salaryText is not a valid number
                JOptionPane.showMessageDialog(null, "Invalid salary format. Please enter a valid integer.");
                return; // Exit the method
            }
        }

        // Execute the query
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Employee added successfully!");
            clearFields();

            // Optionally open the ViewAllEmployeesForm
            SwingUtilities.invokeLater(() -> new ViewAllEmployeesForm().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add employee.");
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
                new EmployeeManagementSystem().setVisible(true);
            }
        });
    }
}
