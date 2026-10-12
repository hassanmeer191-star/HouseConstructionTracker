
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginForm extends JFrame {

private JTextField emailField;
private JPasswordField passwordField;
private JComboBox<String> roleComboBox;
private JCheckBox showPasswordCheckBox;

private JLabel emailErrorLabel;
private JLabel passwordErrorLabel;
private JLabel passwordLengthLabel;

private final Color normalBorder = new Color(200, 200, 200);
private final Color errorColor = new Color(220, 38, 38);
private final Color successColor = new Color(22, 163, 74);
private final Color blueColor = new Color(30, 60, 114);

public LoginForm() {

setTitle("House Construction Progress Tracker - Login");
setSize(520, 590);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setResizable(false);

JPanel mainPanel = new JPanel(new GridBagLayout());
mainPanel.setBackground(Color.WHITE);

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(6, 10, 6, 10);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1;

JLabel titleLabel = new JLabel(
"House Construction Progress Tracker",
SwingConstants.CENTER);
titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
titleLabel.setForeground(blueColor);

gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 2;
mainPanel.add(titleLabel, gbc);

JLabel subtitleLabel = new JLabel(
"Sign in to your account",
SwingConstants.CENTER);
subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

gbc.gridy = 1;
mainPanel.add(subtitleLabel, gbc);

gbc.gridwidth = 1;

addLabel(mainPanel, gbc, "Login As", 2);

roleComboBox = new JComboBox<>(
new String[]{"Homeowner", "Supervisor", "Admin"});
roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
roleComboBox.setPreferredSize(new Dimension(200, 34));
addComponent(mainPanel, gbc, roleComboBox, 2);

addLabel(mainPanel, gbc, "Email Address", 3);

emailField = new JTextField();
emailField.setFont(new Font("Arial", Font.PLAIN, 14));
emailField.setBorder(new LineBorder(normalBorder, 1));
emailField.setPreferredSize(new Dimension(200, 34));
addComponent(mainPanel, gbc, emailField, 3);

emailErrorLabel = new JLabel(" ");
emailErrorLabel.setForeground(errorColor);
emailErrorLabel.setFont(new Font("Arial", Font.PLAIN, 11));

gbc.gridx = 1;
gbc.gridy = 4;
mainPanel.add(emailErrorLabel, gbc);

addLabel(mainPanel, gbc, "Password", 5);

passwordField = new JPasswordField();
passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
passwordField.setBorder(new LineBorder(normalBorder, 1));
passwordField.setPreferredSize(new Dimension(200, 34));
addComponent(mainPanel, gbc, passwordField, 5);

passwordErrorLabel = new JLabel(" ");
passwordErrorLabel.setForeground(errorColor);
passwordErrorLabel.setFont(new Font("Arial", Font.PLAIN, 11));

gbc.gridx = 1;
gbc.gridy = 6;
mainPanel.add(passwordErrorLabel, gbc);

passwordLengthLabel = new JLabel(
"Password must contain at least 8 characters.");
passwordLengthLabel.setForeground(Color.GRAY);
passwordLengthLabel.setFont(new Font("Arial", Font.PLAIN, 11));

gbc.gridx = 1;
gbc.gridy = 7;
mainPanel.add(passwordLengthLabel, gbc);

showPasswordCheckBox = new JCheckBox("Show Password");
showPasswordCheckBox.setBackground(Color.WHITE);

gbc.gridx = 1;
gbc.gridy = 8;
mainPanel.add(showPasswordCheckBox, gbc);

JButton signInButton = new JButton("Sign In");
signInButton.setFont(new Font("Arial", Font.BOLD, 15));
signInButton.setBackground(blueColor);
signInButton.setForeground(Color.WHITE);
signInButton.setFocusPainted(false);
signInButton.setPreferredSize(new Dimension(200, 38));

gbc.gridx = 0;
gbc.gridy = 9;
gbc.gridwidth = 2;
mainPanel.add(signInButton, gbc);

JLabel helpLabel = new JLabel(
"Contact your company administrator for login details.",
SwingConstants.CENTER);
helpLabel.setFont(new Font("Arial", Font.PLAIN, 11));
helpLabel.setForeground(Color.GRAY);

gbc.gridy = 10;
mainPanel.add(helpLabel, gbc);

// Live email validation
emailField.getDocument().addDocumentListener(
new javax.swing.event.DocumentListener() {

@Override
public void insertUpdate(
javax.swing.event.DocumentEvent e) {
validateEmail();
}

@Override
public void removeUpdate(
javax.swing.event.DocumentEvent e) {
validateEmail();
}

@Override
public void changedUpdate(
javax.swing.event.DocumentEvent e) {
validateEmail();
}
});

// Live password validation
passwordField.getDocument().addDocumentListener(
new javax.swing.event.DocumentListener() {

@Override
public void insertUpdate(
javax.swing.event.DocumentEvent e) {
validatePassword();
}

@Override
public void removeUpdate(
javax.swing.event.DocumentEvent e) {
validatePassword();
}

@Override
public void changedUpdate(
javax.swing.event.DocumentEvent e) {
validatePassword();
}
});

// Show or hide password
showPasswordCheckBox.addActionListener(e -> {
if (showPasswordCheckBox.isSelected()) {
passwordField.setEchoChar((char) 0);
} else {
passwordField.setEchoChar('\u2022');
}
});

signInButton.addActionListener(e -> loginUser());

add(mainPanel);
setVisible(true);
}

private void addLabel(JPanel panel,
  GridBagConstraints gbc,
  String text, int row) {

gbc.gridx = 0;
gbc.gridy = row;
gbc.gridwidth = 1;
panel.add(new JLabel(text), gbc);
}

private void addComponent(JPanel panel,
      GridBagConstraints gbc,
      JComponent component, int row) {

gbc.gridx = 1;
gbc.gridy = row;
gbc.gridwidth = 1;
panel.add(component, gbc);
}

// Validate email format
private boolean validateEmail() {

String email = emailField.getText().trim();

if (email.isEmpty()) {
emailField.setBorder(new LineBorder(errorColor, 2));
emailErrorLabel.setForeground(errorColor);
emailErrorLabel.setText("Email address is required.");
return false;
}

if (!email.matches(
"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

emailField.setBorder(new LineBorder(errorColor, 2));
emailErrorLabel.setForeground(errorColor);
emailErrorLabel.setText("Enter a valid email address.");
return false;
}

emailField.setBorder(new LineBorder(successColor, 2));
emailErrorLabel.setForeground(successColor);
emailErrorLabel.setText("Email format is valid.");
return true;
}

// Validate password length
private boolean validatePassword() {

String password = new String(passwordField.getPassword());

if (password.isEmpty()) {
passwordField.setBorder(new LineBorder(errorColor, 2));
passwordErrorLabel.setForeground(errorColor);
passwordErrorLabel.setText("Password is required.");
passwordLengthLabel.setForeground(Color.GRAY);
passwordLengthLabel.setText(
"Password must contain at least 8 characters.");
return false;
}

if (password.length() < 8) {
passwordField.setBorder(new LineBorder(errorColor, 2));
passwordErrorLabel.setForeground(errorColor);
passwordErrorLabel.setText("Password is too short.");
passwordLengthLabel.setForeground(errorColor);
passwordLengthLabel.setText(
password.length() + "/8 characters. Need "
+ (8 - password.length()) + " more.");
return false;
}

passwordField.setBorder(new LineBorder(successColor, 2));
passwordErrorLabel.setForeground(successColor);
passwordErrorLabel.setText("Password length is valid.");
passwordLengthLabel.setForeground(successColor);
passwordLengthLabel.setText(
password.length() + " characters. Minimum met.");
return true;
}

// Check credentials in users.txt
private boolean checkCredentials(String email,
             String password,
             String selectedRole)
throws IOException {

File file = new File("users.txt");

// Print the exact path being checked in NetBeans Output
System.out.println("Looking for users.txt at: "
+ file.getAbsolutePath());
System.out.println("File exists: " + file.exists());
System.out.println("Can read file: " + file.canRead());

try (BufferedReader reader =
new BufferedReader(new FileReader(file))) {

String line;

while ((line = reader.readLine()) != null) {

String[] details = line.split(",", -1);

if (details.length != 3) {
continue;
}

String savedEmail = details[0].trim();
String savedPassword = details[1].trim();
String savedRole = details[2].trim();

if (email.equalsIgnoreCase(savedEmail)
&& password.equals(savedPassword)
&& selectedRole.equalsIgnoreCase(savedRole)) {
return true;
}
}
}

return false;
}

// Handle login
private void loginUser() {

boolean emailValid = validateEmail();
boolean passwordValid = validatePassword();

if (!emailValid || !passwordValid) {
JOptionPane.showMessageDialog(this,
"Please correct the highlighted fields.",
"Invalid Input",
JOptionPane.ERROR_MESSAGE);
return;
}

String email = emailField.getText().trim();
String password = new String(passwordField.getPassword());
String role = roleComboBox.getSelectedItem().toString();

try {

if (checkCredentials(email, password, role)) {

JOptionPane.showMessageDialog(this,
"Login successful!\nWelcome, " + role + ".",
"Success",
JOptionPane.INFORMATION_MESSAGE);

// The selected dashboard will be opened here later.

} else {

JOptionPane.showMessageDialog(this,
"Wrong email, password, or selected role.",
"Login Failed",
JOptionPane.ERROR_MESSAGE);
}

} catch (IOException e) {

JOptionPane.showMessageDialog(this,
"Could not read users.txt.\n"
+ "Check the file location and name.",
"File Error",
JOptionPane.ERROR_MESSAGE);
}
}

public static void main(String[] args) {

SwingUtilities.invokeLater(() -> new LoginForm());
}
}
