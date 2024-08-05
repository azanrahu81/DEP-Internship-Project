import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class ViewAllEmployeesForm extends JFrame {

    // Declare Swing components for the form
    JTable employeeTable;
    JButton refreshButton;
    JButton backButton;
    Color color;
    Font font;

    public ViewAllEmployeesForm() {
        // Set up the JFrame
        setTitle("View All Employees");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color color = new Color(173, 216, 230); // RGB for dark blue
        getContentPane().setBackground(color);

        JLabel l1 = new JLabel("Employee List");
        l1.setBounds(350, 10, 200, 30);
        l1.setFont(new Font("Serif", Font.BOLD, 30));
        l1.setForeground(Color.BLACK);
        add(l1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 900, 20);
        add(separator);

        // Initialize and add Swing components
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(50, 70, 800, 300);
        add(scrollPane);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(350, 390, 100, 30);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBackground(color);
        add(refreshButton);

        backButton = new JButton("Back");
        backButton.setBounds(480, 390, 100, 30);
        backButton.setBackground(color);
        backButton.setForeground(Color.BLACK);

        add(backButton);

        // Add ActionListener to the refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchEmployees();
            }
        });

        // Add ActionListener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditEmployee().setVisible(true);
                dispose(); // Close the View All Employees form and go back
            }
        });

        // Fetch employees data initially
        fetchEmployees();
    }

    // Method to fetch employee data from the database
    private void fetchEmployees() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Add_e";
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
            String sql = "SELECT id, name, position, department, salary FROM employee1";
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Get the ResultSet metadata
            ResultSetMetaData metaData = resultSet.getMetaData();

            // Get the number of columns
            int columnCount = metaData.getColumnCount();

            // Get column names
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Get data
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            // Set the table model
            employeeTable.setModel(model);

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
                new ViewAllEmployeesForm().setVisible(true);
            }
        });
    }
}
