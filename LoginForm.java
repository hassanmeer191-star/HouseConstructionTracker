import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginForm extends JFrame {

    // ===== Colors (same as the dashboards) =====
    private static final Color NAVY = new Color(0x17, 0x2B, 0x4D);
    private static final Color BLUE = new Color(0x2F, 0x65, 0xC8);
    private static final Color BG   = new Color(0xF5, 0xF7, 0xFB);
    private static final Color GRAY = new Color(0x6B, 0x77, 0x85);
    private static final Color TEXT = new Color(0x1F, 0x2A, 0x37);
    private static final Color RED  = new Color(0xC0, 0x39, 0x2B);

    // TEMPORARY login file (will be replaced by SQL Server later)
    private static final String USERS_FILE = "users.txt";

    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JComboBox<String> roleBox;
    private JLabel messageLabel;

    public LoginForm() {
        setTitle("BuildTrack - Login");
        setSize(900, 560);
        setMinimumSize(new Dimension(800, 520));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        add(createLeftPanel());
        add(createRightPanel());
    }

    // =====================================================
    //  LEFT SIDE: brand
    // =====================================================
    private JPanel createLeftPanel() {
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(NAVY);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);

        JLabel brand = new JLabel("BuildTrack");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 40));
        brand.setForeground(Color.WHITE);

        JLabel title = new JLabel("House Construction Progress Tracker");
        title.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        title.setForeground(new Color(0xA9, 0xB7, 0xD0));

        JLabel line = new JLabel("Track progress, budget and photos in one place.");
        line.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        line.setForeground(new Color(0xA9, 0xB7, 0xD0));

        box.add(brand);
        box.add(Box.createVerticalStrut(10));
        box.add(title);
        box.add(Box.createVerticalStrut(24));
        box.add(line);

        left.add(box);
        return left;
    }

    // =====================================================
    //  RIGHT SIDE: login form
    // =====================================================
    private JPanel createRightPanel() {
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE3, 0xE8, 0xF0)),
                BorderFactory.createEmptyBorder(28, 32, 28, 32)));
        card.setPreferredSize(new Dimension(360, 440));

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Please log in to continue");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(GRAY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));

        // Email
        card.add(createLabel("Email"));
        emailField = new JTextField();
        styleField(emailField);
        card.add(emailField);
        card.add(Box.createVerticalStrut(12));

        // Password
        card.add(createLabel("Password"));
        passwordField = new JPasswordField();
        styleField(passwordField);
        card.add(passwordField);
        card.add(Box.createVerticalStrut(6));

        // Show password
        final char defaultEcho = passwordField.getEchoChar();
        showPassword = new JCheckBox("Show password");
        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        showPassword.setForeground(GRAY);
        showPassword.setBackground(Color.WHITE);
        showPassword.setFocusPainted(false);
        showPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) passwordField.setEchoChar((char) 0);
            else passwordField.setEchoChar(defaultEcho);
        });
        card.add(showPassword);
        card.add(Box.createVerticalStrut(10));

        // Role
        card.add(createLabel("Login as"));
        roleBox = new JComboBox<>(new String[]{"Homeowner", "Supervisor", "Admin"});
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleBox.setBackground(Color.WHITE);
        roleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        roleBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        card.add(roleBox);
        card.add(Box.createVerticalStrut(14));

        // Error message (empty at first)
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(RED);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(messageLabel);
        card.add(Box.createVerticalStrut(8));

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginBtn.setBackground(BLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.addActionListener(e -> doLogin());
        card.add(loginBtn);

        getRootPane().setDefaultButton(loginBtn);   // Enter key = Login

        right.add(card);
        return right;
    }

    // =====================================================
    //  LOGIN LOGIC
    // =====================================================
    private void doLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        // 1) basic input validation
        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter email and password.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            messageLabel.setText("Please enter a valid email.");
            return;
        }

        // 2) check the file
        try {
            if (!checkCredentials(email, password, role)) {
                messageLabel.setText("Wrong email, password or role.");
                return;
            }
        } catch (IOException ex) {
            messageLabel.setText("Cannot read " + USERS_FILE + " file.");
            return;
        }

        // 3) success: open the dashboard of the selected role
        messageLabel.setText(" ");
        openDashboard(email, role);
    }

    // file format: email,password,role
    private boolean checkCredentials(String email, String password, String role)
            throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 3) continue;     // skip bad lines

                if (parts[0].trim().equalsIgnoreCase(email)
                        && parts[1].trim().equals(password)
                        && parts[2].trim().equalsIgnoreCase(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void openDashboard(String email, String role) {
        if (role.equals("Homeowner")) {
            new HomeownerDashboard(email).setVisible(true);
            dispose();
        } else if (role.equals("Supervisor")) {
            // When SupervisorDashboard.java is in this folder, use:
            // new SupervisorDashboard().setVisible(true);
            // dispose();
            JOptionPane.showMessageDialog(this,
                    "Supervisor login works. SupervisorDashboard will be connected later.");
        } else {
            // When AdminDashboard.java is ready, use:
            // new AdminDashboard().setVisible(true);
            // dispose();
            JOptionPane.showMessageDialog(this,
                    "Admin login works. AdminDashboard will be connected later.");
        }
    }

    // =====================================================
    //  SMALL HELPER METHODS
    // =====================================================
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD5, 0xDB, 0xE5)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}