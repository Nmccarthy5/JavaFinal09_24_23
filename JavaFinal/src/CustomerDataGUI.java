import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CustomerDataGUI {
    private JFrame frame;
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JTextArea displayArea;
    private Connection connection;

    public static void main(String[] args) {
        EventQueue.invokeLater(CustomerDataGUI::new);
    }

    public CustomerDataGUI() {
        initialize();
        connectToDatabase();
    }

    private void initialize() {
        frame = new JFrame("NATO PHOTOGRAPHY DATA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 650, 400);
        frame.setLayout(new GridLayout(8, 2));

        // Create labels and fields
        frame.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        frame.add(firstNameField);

        frame.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        frame.add(lastNameField);

        frame.add(new JLabel("Email:"));
        emailField = new JTextField();
        frame.add(emailField);

        frame.add(new JLabel("Phone number:"));
        phoneField = new JTextField();
        frame.add(phoneField);

        JButton submitButton = new JButton("Add Customer to database");
        submitButton.addActionListener(this::submit);
        frame.add(submitButton);

        JButton queryButton = new JButton("Show Records");
        queryButton.addActionListener(e -> query());
        frame.add(queryButton);

        frame.add(new JLabel("Output:"));
        displayArea = new JTextArea();
        frame.add(new JScrollPane(displayArea));

        frame.setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Customer_Info.db");
            System.out.println("Connected to the database successfully!"); // Diagnostic message
        } catch (Exception e) {
            System.out.println("Error while connecting to the database!"); // Diagnostic message
            e.printStackTrace();
        }
    }

    private void submit(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO addresses (first_Name, last_Name, Email, Phone) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.executeUpdate();

            // Clear text boxes
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            phoneField.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void query() {
        StringBuilder records = new StringBuilder();
    
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM addresses")) {
    
            while (resultSet.next()) {
                records.append(resultSet.getString("first_Name")).append(" ")
                        .append(resultSet.getString("last_Name")).append(" - ")
                        .append(resultSet.getString("Email")).append(" - ")
                        .append(resultSet.getString("Phone")).append("\n");
            }
    
            displayArea.setText(records.toString());
            System.out.println("Query executed successfully!"); // Diagnostic message
    
        } catch (SQLException e) {
            System.out.println("Error during query execution!"); // Diagnostic message
            e.printStackTrace();
        }
    }
}