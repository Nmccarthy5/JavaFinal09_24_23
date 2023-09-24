import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.nio.file.*;

public class NATOPhotographyLoginGUI {
    private JFrame mainFrame;
    private JFrame registerFrame;
    private JFrame loginFrame;

    public static void main(String[] args) {
        new NATOPhotographyLoginGUI();
    }

    public NATOPhotographyLoginGUI() {
        mainAccountScreen();
    }

    private void mainAccountScreen() {
        mainFrame = new JFrame("Account Login");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(6, 1));
        mainFrame.setSize(650, 1240);

        JLabel imageLabel = new JLabel(new ImageIcon("Nathan.png"));
        mainFrame.add(imageLabel);
        mainFrame.add(new JLabel("NATO PHOTOGRAPHY", JLabel.CENTER));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        mainFrame.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> register());
        mainFrame.add(registerButton);

        mainFrame.add(new JLabel("Photo: Silhouette of a man in a fedora", JLabel.CENTER));

        mainFrame.setVisible(true);
    }

    private void register() {
        registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 250);
        registerFrame.setLayout(new GridLayout(6, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        registerFrame.add(new JLabel("Please enter details below", JLabel.CENTER));
        registerFrame.add(new JLabel("Username"));
        registerFrame.add(usernameField);
        registerFrame.add(new JLabel("Password"));
        registerFrame.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try (PrintWriter out = new PrintWriter(username)) {
                out.println(username);
                out.println(password);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            JOptionPane.showMessageDialog(registerFrame, "Registration Success");

            usernameField.setText("");
            passwordField.setText("");
        });
        registerFrame.add(registerBtn);

        registerFrame.setVisible(true);
    }

    private void login() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 250);
        loginFrame.setLayout(new GridLayout(6, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        loginFrame.add(new JLabel("Please enter details below to login", JLabel.CENTER));
        loginFrame.add(new JLabel("Username"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password"));
        loginFrame.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Path path = Paths.get(username);
            if (Files.exists(path)) {
                try {
                    List<String> lines = Files.readAllLines(path);
                    if (lines.size() > 1 && lines.get(1).equals(password)) {
                        loginSuccess();
                    } else {
                        passwordNotRecognised();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                userNotFound();
            }

            usernameField.setText("");
            passwordField.setText("");
        });
        loginFrame.add(loginBtn);

        loginFrame.setVisible(true);
    }

    private void loginSuccess() {
        JFrame successFrame = new JFrame("Success");
        successFrame.setSize(300, 250);

        successFrame.add(new JLabel("Login Success", JLabel.CENTER));

        JButton dataBtn = new JButton("View Customer Data");
        dataBtn.addActionListener(e -> runProgram());
        successFrame.add(dataBtn);

        successFrame.setVisible(true);
    }

    private void passwordNotRecognised() {
        JOptionPane.showMessageDialog(loginFrame, "Invalid Password");
    }

    private void userNotFound() {
        JOptionPane.showMessageDialog(loginFrame, "User Not Found");
    }

    private void runProgram() {
        new CustomerDataGUI();
    }
}