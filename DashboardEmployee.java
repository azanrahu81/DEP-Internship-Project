import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DashboardEmployee extends JFrame {
    // Declare Swing components
    private JButton addButton, viewButton, deleteButton, editButton;

    public DashboardEmployee() {
        // Set up the JFrame
        setTitle("Employee Management System");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color color = new Color(173, 216, 230); // RGB for light blue
        getContentPane().setBackground(color);

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setBounds(250, 5, 400, 50);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 70, 900, 20);
        add(separator);

        // Initialize and add buttons
        addButton = new JButton("Add Employee");
        addButton.setBackground(color);
        addButton.setBounds(200, 150, 200, 50);
        add(addButton);

        viewButton = new JButton("View Employees");
        viewButton.setBackground(color);
        viewButton.setBounds(500, 150, 200, 50);
        add(viewButton);

        editButton = new JButton("Edit Employee");
        editButton.setBackground(color);
        editButton.setBounds(200, 250, 200, 50);
        add(editButton);

        deleteButton = new JButton("Delete Employee");
        deleteButton.setBackground(color);
        deleteButton.setBounds(500, 250, 200, 50);
        add(deleteButton);

        // Add ActionListeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeManagementSystem().setVisible(true);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAllEmployeesForm().setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditEmployee().setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployee().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardEmployee().setVisible(true);
            }
        });
    }
}
