
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContactUsPage extends JFrame {

private final Color NAVY = new Color(23, 43, 77);
private final Color BLUE = new Color(47, 101, 200);
private final Color BACKGROUND = new Color(245, 247, 251);
private final Color TEXT = new Color(45, 55, 72);
private final Color GREEN = new Color(37, 145, 93);

public ContactUsPage() {

setTitle("BuildTrack - Contact Us");
setSize(1100, 750);
setMinimumSize(new Dimension(900, 650));
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
setLocationRelativeTo(null);

JPanel mainPanel = new JPanel(new BorderLayout());
mainPanel.setBackground(BACKGROUND);

mainPanel.add(createHeader(), BorderLayout.NORTH);

JPanel content = new JPanel(new BorderLayout(25, 25));
content.setBackground(BACKGROUND);
content.setBorder(new EmptyBorder(35, 45, 35, 45));

content.add(createIntroduction(), BorderLayout.NORTH);

JPanel centerPanel = new JPanel(new GridLayout(1, 2, 25, 0));
centerPanel.setBackground(BACKGROUND);

centerPanel.add(createContactInformation());
centerPanel.add(createContactForm());

content.add(centerPanel, BorderLayout.CENTER);

JScrollPane scrollPane = new JScrollPane(content);
scrollPane.setBorder(null);
scrollPane.getVerticalScrollBar().setUnitIncrement(16);
scrollPane.getViewport().setBackground(BACKGROUND);

mainPanel.add(scrollPane, BorderLayout.CENTER);
mainPanel.add(createFooter(), BorderLayout.SOUTH);

add(mainPanel);
setVisible(true);
}

private JPanel createHeader() {

JPanel header = new JPanel(new BorderLayout());
header.setBackground(NAVY);
header.setBorder(new EmptyBorder(18, 35, 18, 35));

JLabel logo = new JLabel("BuildTrack");
logo.setFont(new Font("Arial", Font.BOLD, 25));
logo.setForeground(Color.WHITE);

JLabel subtitle = new JLabel("House Construction Progress Tracker");
subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
subtitle.setForeground(new Color(200, 215, 235));

JPanel branding = new JPanel();
branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
branding.setBackground(NAVY);

branding.add(logo);
branding.add(Box.createVerticalStrut(4));
branding.add(subtitle);

JButton homeButton = new JButton("Back to Dashboard");
homeButton.setFocusPainted(false);
homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

homeButton.addActionListener(e -> dispose());

header.add(branding, BorderLayout.WEST);
header.add(homeButton, BorderLayout.EAST);

return header;
}

private JPanel createIntroduction() {

JPanel panel = new JPanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBackground(BACKGROUND);

JLabel heading = new JLabel("Contact Us");
heading.setFont(new Font("Arial", Font.BOLD, 32));
heading.setForeground(TEXT);
heading.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel description = new JLabel(
"<html>Have a question about your construction project? "
+ "We are here to help.<br>"
+ "Send us a message using the form below.</html>");

description.setFont(new Font("Arial", Font.PLAIN, 15));
description.setForeground(new Color(105, 115, 130));
description.setBorder(new EmptyBorder(10, 0, 0, 0));
description.setAlignmentX(Component.LEFT_ALIGNMENT);

panel.add(heading);
panel.add(description);

return panel;
}

private JPanel createContactInformation() {

JPanel panel = createCard();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBorder(new EmptyBorder(25, 25, 25, 25));

JLabel heading = new JLabel("Get in Touch");
heading.setFont(new Font("Arial", Font.BOLD, 22));
heading.setForeground(TEXT);

JLabel description = new JLabel(
"<html>Contact our team for assistance with<br>"
+ "your construction tracking needs.</html>");

description.setFont(new Font("Arial", Font.PLAIN, 13));
description.setForeground(Color.GRAY);

panel.add(heading);
panel.add(Box.createVerticalStrut(10));
panel.add(description);
panel.add(Box.createVerticalStrut(30));

panel.add(createInfoItem(
"EMAIL ADDRESS",
"support@buildtrack.com",
"For general questions and support"));

panel.add(Box.createVerticalStrut(25));

panel.add(createInfoItem(
"PHONE NUMBER",
"+92 XXX XXXXXXX",
"For direct assistance"));

panel.add(Box.createVerticalStrut(25));

panel.add(createInfoItem(
"OFFICE LOCATION",
"Rawalpindi, Pakistan",
"Our project support location"));

panel.add(Box.createVerticalStrut(30));

JPanel supportBox = new JPanel();
supportBox.setLayout(new BoxLayout(supportBox, BoxLayout.Y_AXIS));
supportBox.setBackground(new Color(235, 243, 255));
supportBox.setBorder(new EmptyBorder(18, 18, 18, 18));
supportBox.setAlignmentX(Component.LEFT_ALIGNMENT);
supportBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

JLabel supportTitle = new JLabel("Construction Project Support");
supportTitle.setFont(new Font("Arial", Font.BOLD, 14));
supportTitle.setForeground(BLUE);

JLabel supportText = new JLabel(
"<html>Need help with progress updates, project<br>"
+ "information, or account access? Send us<br>"
+ "a message and describe your issue.</html>");

supportText.setFont(new Font("Arial", Font.PLAIN, 12));
supportText.setForeground(TEXT);

supportBox.add(supportTitle);
supportBox.add(Box.createVerticalStrut(8));
supportBox.add(supportText);

panel.add(supportBox);
panel.add(Box.createVerticalGlue());

return panel;
}

private JPanel createInfoItem(
String heading, String value, String description) {

JPanel item = new JPanel();
item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
item.setBackground(Color.WHITE);
item.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel headingLabel = new JLabel(heading);
headingLabel.setFont(new Font("Arial", Font.BOLD, 10));
headingLabel.setForeground(BLUE);

JLabel valueLabel = new JLabel(value);
valueLabel.setFont(new Font("Arial", Font.BOLD, 15));
valueLabel.setForeground(TEXT);
valueLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

JLabel descriptionLabel = new JLabel(description);
descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
descriptionLabel.setForeground(Color.GRAY);

item.add(headingLabel);
item.add(valueLabel);
item.add(descriptionLabel);

return item;
}

private JPanel createContactForm() {

JPanel panel = createCard();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBorder(new EmptyBorder(25, 25, 25, 25));

JLabel heading = new JLabel("Send Us a Message");
heading.setFont(new Font("Arial", Font.BOLD, 22));
heading.setForeground(TEXT);

JLabel subtitle = new JLabel("Fill in your details below.");
subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
subtitle.setForeground(Color.GRAY);

panel.add(heading);
panel.add(Box.createVerticalStrut(8));
panel.add(subtitle);
panel.add(Box.createVerticalStrut(22));

JTextField nameField = createTextField("Enter your full name");
JTextField emailField = createTextField("Enter your email address");

String[] subjects = {
"General Inquiry",
"Construction Progress",
"Budget and Expenses",
"Account Support",
"Technical Issue",
"Other"
};

JComboBox<String> subjectBox = new JComboBox<>(subjects);
subjectBox.setFont(new Font("Arial", Font.PLAIN, 13));
subjectBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
subjectBox.setBackground(Color.WHITE);
subjectBox.setAlignmentX(Component.LEFT_ALIGNMENT);

JTextArea messageArea = new JTextArea(5, 20);
messageArea.setFont(new Font("Arial", Font.PLAIN, 13));
messageArea.setLineWrap(true);
messageArea.setWrapStyleWord(true);
messageArea.setBorder(new EmptyBorder(10, 10, 10, 10));

JScrollPane messageScroll = new JScrollPane(messageArea);
messageScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
messageScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

addFormField(panel, "Full Name", nameField);
addFormField(panel, "Email Address", emailField);
addFormField(panel, "Subject", subjectBox);
addFormField(panel, "Message", messageScroll);

JButton sendButton = new JButton("Send Message");
sendButton.setFont(new Font("Arial", Font.BOLD, 14));
sendButton.setForeground(Color.WHITE);
sendButton.setBackground(BLUE);
sendButton.setFocusPainted(false);
sendButton.setBorder(new EmptyBorder(12, 20, 12, 20));
sendButton.setAlignmentX(Component.LEFT_ALIGNMENT);
sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

sendButton.addActionListener(e -> {
JOptionPane.showMessageDialog(
this,
"The contact form is currently a demo. "
+ "Message sending will be added later.",
"Contact Us",
JOptionPane.INFORMATION_MESSAGE);
});

panel.add(Box.createVerticalStrut(8));
panel.add(sendButton);

return panel;
}

private void addFormField(
JPanel panel, String labelText, JComponent field) {

JLabel label = new JLabel(labelText);
label.setFont(new Font("Arial", Font.BOLD, 12));
label.setForeground(TEXT);
label.setAlignmentX(Component.LEFT_ALIGNMENT);

field.setAlignmentX(Component.LEFT_ALIGNMENT);
field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

panel.add(label);
panel.add(Box.createVerticalStrut(6));
panel.add(field);
panel.add(Box.createVerticalStrut(14));
}

private JTextField createTextField(String placeholder) {

JTextField field = new JTextField();
field.setFont(new Font("Arial", Font.PLAIN, 13));
field.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(
    new Color(220, 225, 233)),
new EmptyBorder(8, 10, 8, 10)));

field.setToolTipText(placeholder);

return field;
}

private JPanel createCard() {

JPanel panel = new JPanel();
panel.setBackground(Color.WHITE);
panel.setBorder(BorderFactory.createLineBorder(
new Color(230, 234, 240)));

return panel;
}

private JPanel createFooter() {

JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
footer.setBackground(Color.WHITE);
footer.setBorder(new EmptyBorder(12, 10, 12, 10));

JLabel text = new JLabel(
"BuildTrack | House Construction Progress Tracker");
text.setFont(new Font("Arial", Font.PLAIN, 11));
text.setForeground(Color.GRAY);

footer.add(text);

return footer;
}

public static void main(String[] args) {

SwingUtilities.invokeLater(() -> new ContactUsPage());
}
}
