
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SupervisorDashboard extends JFrame {

private final Color SIDEBAR = new Color(23, 43, 77);
private final Color BLUE = new Color(47, 101, 200);
private final Color BACKGROUND = new Color(245, 247, 251);
private final Color TEXT = new Color(45, 55, 72);
private final Color GREEN = new Color(37, 145, 93);
private final Color ORANGE = new Color(220, 145, 42);

private JPanel contentPanel;

public SupervisorDashboard() {

setTitle("BuildTrack - Supervisor Dashboard");
setSize(1200, 780);
setMinimumSize(new Dimension(1000, 650));
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);

JPanel mainPanel = new JPanel(new BorderLayout());
mainPanel.setBackground(BACKGROUND);

mainPanel.add(createSidebar(), BorderLayout.WEST);

JPanel rightPanel = new JPanel(new BorderLayout());
rightPanel.setBackground(BACKGROUND);

rightPanel.add(createTopBar(), BorderLayout.NORTH);

contentPanel = new JPanel(new BorderLayout(20, 20));
contentPanel.setBackground(BACKGROUND);
contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

contentPanel.add(createDashboardContent(), BorderLayout.CENTER);

JScrollPane scrollPane = new JScrollPane(contentPanel);
scrollPane.setBorder(null);
scrollPane.getVerticalScrollBar().setUnitIncrement(16);
scrollPane.getViewport().setBackground(BACKGROUND);

rightPanel.add(scrollPane, BorderLayout.CENTER);
mainPanel.add(rightPanel, BorderLayout.CENTER);

add(mainPanel);
setVisible(true);
}

private JPanel createSidebar() {

JPanel sidebar = new JPanel();
sidebar.setPreferredSize(new Dimension(235, 0));
sidebar.setBackground(SIDEBAR);
sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
sidebar.setBorder(new EmptyBorder(22, 14, 18, 14));

JLabel logo = new JLabel("  BuildTrack");
logo.setFont(new Font("Arial", Font.BOLD, 25));
logo.setForeground(Color.WHITE);
logo.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel workspace = new JLabel("SUPERVISOR WORKSPACE");
workspace.setFont(new Font("Arial", Font.BOLD, 10));
workspace.setForeground(new Color(170, 190, 220));
workspace.setBorder(new EmptyBorder(8, 10, 25, 0));
workspace.setAlignmentX(Component.LEFT_ALIGNMENT);

sidebar.add(logo);
sidebar.add(workspace);

String[] menuItems = {
"Overview",
"Construction Projects",
"Construction Stages",
"Progress Updates",
"Daily Work Status",
"Homeowner Questions",
"Budget and Expenses",
"Workers Management",
"Site Photos",
"Reports and History",
"Settings"
};

for (String item : menuItems) {
JButton button = createMenuButton(item);

if (item.equals("Overview")) {
button.setBackground(new Color(47, 101, 200));
button.setForeground(Color.WHITE);
}

button.addActionListener(e -> showSection(item));
sidebar.add(button);
sidebar.add(Box.createVerticalStrut(5));
}

sidebar.add(Box.createVerticalGlue());

JLabel profile = new JLabel(
"<html><b>Muhammad Hassan</b><br>"
+ "<font color='#B9C7DD'>Supervisor</font></html>");

profile.setForeground(Color.WHITE);
profile.setFont(new Font("Arial", Font.PLAIN, 12));
profile.setBorder(new EmptyBorder(12, 8, 15, 0));
profile.setAlignmentX(Component.LEFT_ALIGNMENT);

JButton logoutButton = createMenuButton("Logout");
logoutButton.addActionListener(e -> {
dispose();
new LoginForm();
});

sidebar.add(profile);
sidebar.add(logoutButton);

return sidebar;
}

private JButton createMenuButton(String text) {

JButton button = new JButton(text);
button.setFont(new Font("Arial", Font.PLAIN, 13));
button.setForeground(new Color(220, 228, 240));
button.setBackground(SIDEBAR);
button.setHorizontalAlignment(SwingConstants.LEFT);
button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
button.setPreferredSize(new Dimension(205, 40));
button.setBorder(new EmptyBorder(8, 12, 8, 5));
button.setFocusPainted(false);
button.setCursor(new Cursor(Cursor.HAND_CURSOR));

return button;
}

private JPanel createTopBar() {

JPanel topBar = new JPanel(new BorderLayout());
topBar.setBackground(Color.WHITE);
topBar.setBorder(new EmptyBorder(18, 25, 18, 25));

JLabel pageLabel = new JLabel("Workspace / Overview");
pageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
pageLabel.setForeground(new Color(110, 120, 135));

JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
rightControls.setBackground(Color.WHITE);

JTextField searchField = new JTextField("Search projects...");
searchField.setPreferredSize(new Dimension(180, 32));
searchField.setForeground(Color.GRAY);

JButton notificationButton = new JButton("Notifications");
notificationButton.setFocusPainted(false);

rightControls.add(searchField);
rightControls.add(notificationButton);

topBar.add(pageLabel, BorderLayout.WEST);
topBar.add(rightControls, BorderLayout.EAST);

return topBar;
}

private JPanel createDashboardContent() {

JPanel dashboard = new JPanel();
dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.Y_AXIS));
dashboard.setBackground(BACKGROUND);

JLabel heading = new JLabel("Good morning, Muhammad!");
heading.setFont(new Font("Arial", Font.BOLD, 27));
heading.setForeground(TEXT);
heading.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel subtitle = new JLabel(
"Here's what's happening across your construction sites today.");

subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
subtitle.setForeground(Color.GRAY);
subtitle.setBorder(new EmptyBorder(7, 0, 22, 0));
subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

dashboard.add(heading);
dashboard.add(subtitle);

JPanel stats = new JPanel(new GridLayout(1, 4, 15, 0));
stats.setBackground(BACKGROUND);
stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));
stats.setAlignmentX(Component.LEFT_ALIGNMENT);

stats.add(createStatCard("Active Projects", "3", "Currently managed", BLUE));
stats.add(createStatCard("Average Progress", "65%", "Across all projects", GREEN));
stats.add(createStatCard("Pending Questions", "4", "Need a reply", ORANGE));
stats.add(createStatCard("Today's Work", "In Progress", "Site activity", BLUE));

dashboard.add(stats);
dashboard.add(Box.createVerticalStrut(22));

JPanel projectPanel = createProjectPanel();
projectPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
dashboard.add(projectPanel);

dashboard.add(Box.createVerticalStrut(20));

JPanel lowerPanels = new JPanel(new GridLayout(1, 2, 18, 0));
lowerPanels.setBackground(BACKGROUND);
lowerPanels.setAlignmentX(Component.LEFT_ALIGNMENT);

lowerPanels.add(createActivityPanel());
lowerPanels.add(createQuestionsPanel());

dashboard.add(lowerPanels);
dashboard.add(Box.createVerticalStrut(20));

JLabel quickTitle = new JLabel("Quick Actions");
quickTitle.setFont(new Font("Arial", Font.BOLD, 19));
quickTitle.setForeground(TEXT);
quickTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

dashboard.add(quickTitle);
dashboard.add(Box.createVerticalStrut(12));

JPanel quickActions = new JPanel(new GridLayout(1, 4, 12, 0));
quickActions.setBackground(BACKGROUND);
quickActions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
quickActions.setAlignmentX(Component.LEFT_ALIGNMENT);

quickActions.add(createActionButton("Update Progress"));
quickActions.add(createActionButton("Daily Work Status"));
quickActions.add(createActionButton("Upload Site Photos"));
quickActions.add(createActionButton("Manage Workers"));

dashboard.add(quickActions);

return dashboard;
}

private JPanel createStatCard(
String title, String value, String description, Color accent) {

JPanel card = new JPanel();
card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
card.setBackground(Color.WHITE);
card.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(new Color(230, 234, 240)),
new EmptyBorder(15, 15, 12, 12)));

JLabel titleLabel = new JLabel(title);
titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
titleLabel.setForeground(Color.GRAY);

JLabel valueLabel = new JLabel(value);
valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
valueLabel.setForeground(accent);
valueLabel.setBorder(new EmptyBorder(8, 0, 5, 0));

JLabel descLabel = new JLabel(description);
descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
descLabel.setForeground(Color.GRAY);

card.add(titleLabel);
card.add(valueLabel);
card.add(descLabel);

return card;
}

private JPanel createProjectPanel() {

JPanel panel = createWhitePanel();
panel.setLayout(new BorderLayout(10, 12));
panel.setBorder(new EmptyBorder(18, 18, 18, 18));

JLabel title = new JLabel("Construction Projects");
title.setFont(new Font("Arial", Font.BOLD, 19));
title.setForeground(TEXT);

JButton viewAll = new JButton("View All Projects");
viewAll.addActionListener(e -> showSection("Construction Projects"));

JPanel heading = new JPanel(new BorderLayout());
heading.setBackground(Color.WHITE);
heading.add(title, BorderLayout.WEST);
heading.add(viewAll, BorderLayout.EAST);

panel.add(heading, BorderLayout.NORTH);

JPanel projects = new JPanel(new GridLayout(3, 1, 0, 8));
projects.setBackground(Color.WHITE);

projects.add(createProjectRow(
"House 12 - Bahria Town",
"Homeowner: Ali Ahmed",
65, "On Track", GREEN));

projects.add(createProjectRow(
"House 08 - DHA Phase 2",
"Homeowner: Usman Khan",
42, "Needs Attention", ORANGE));

projects.add(createProjectRow(
"House 21 - Gulraiz Housing",
"Homeowner: Ahmed Raza",
88, "Near Completion", BLUE));

panel.add(projects, BorderLayout.CENTER);

return panel;
}

private JPanel createProjectRow(
String name, String homeowner, int progress,
String status, Color statusColor) {

JPanel row = new JPanel(new BorderLayout(15, 5));
row.setBackground(Color.WHITE);
row.setBorder(new EmptyBorder(8, 0, 8, 0));

JPanel details = new JPanel();
details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
details.setBackground(Color.WHITE);

JLabel nameLabel = new JLabel(name);
nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
nameLabel.setForeground(TEXT);

JLabel homeownerLabel = new JLabel(homeowner);
homeownerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
homeownerLabel.setForeground(Color.GRAY);

details.add(nameLabel);
details.add(Box.createVerticalStrut(5));
details.add(homeownerLabel);

JPanel progressPanel = new JPanel(new BorderLayout(8, 5));
progressPanel.setBackground(Color.WHITE);
progressPanel.setPreferredSize(new Dimension(240, 45));

JProgressBar bar = new JProgressBar(0, 100);
bar.setValue(progress);
bar.setStringPainted(true);
bar.setString(progress + "%");
bar.setForeground(statusColor);
bar.setBackground(new Color(235, 238, 243));

JLabel statusLabel = new JLabel(status);
statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
statusLabel.setForeground(statusColor);

progressPanel.add(bar, BorderLayout.CENTER);
progressPanel.add(statusLabel, BorderLayout.SOUTH);

row.add(details, BorderLayout.CENTER);
row.add(progressPanel, BorderLayout.EAST);

return row;
}

private JPanel createActivityPanel() {

JPanel panel = createWhitePanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBorder(new EmptyBorder(18, 18, 18, 18));

JLabel title = new JLabel("Recent Activity");
title.setFont(new Font("Arial", Font.BOLD, 18));
title.setForeground(TEXT);
title.setBorder(new EmptyBorder(0, 0, 15, 0));

panel.add(title);
panel.add(createActivityItem(
"Progress updated", "House 12 progress changed to 65%", "Today"));
panel.add(createActivityItem(
"New homeowner question", "A question was received", "Today"));
panel.add(createActivityItem(
"Site photo uploaded", "New construction photo added", "Yesterday"));
panel.add(createActivityItem(
"Daily status updated", "Work marked as in progress", "Yesterday"));

return panel;
}

private JPanel createActivityItem(
String title, String description, String time) {

JPanel item = new JPanel(new BorderLayout(5, 4));
item.setBackground(Color.WHITE);
item.setBorder(new EmptyBorder(8, 0, 8, 0));

JPanel textPanel = new JPanel();
textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
textPanel.setBackground(Color.WHITE);

JLabel titleLabel = new JLabel(title);
titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
titleLabel.setForeground(TEXT);

JLabel descLabel = new JLabel(description);
descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
descLabel.setForeground(Color.GRAY);

textPanel.add(titleLabel);
textPanel.add(Box.createVerticalStrut(4));
textPanel.add(descLabel);

JLabel timeLabel = new JLabel(time);
timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
timeLabel.setForeground(Color.GRAY);

item.add(textPanel, BorderLayout.CENTER);
item.add(timeLabel, BorderLayout.EAST);

return item;
}

private JPanel createQuestionsPanel() {

JPanel panel = createWhitePanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBorder(new EmptyBorder(18, 18, 18, 18));

JLabel title = new JLabel("Homeowner Questions");
title.setFont(new Font("Arial", Font.BOLD, 18));
title.setForeground(TEXT);
title.setBorder(new EmptyBorder(0, 0, 15, 0));

panel.add(title);

panel.add(createQuestionItem(
"Ali Ahmed - House 12",
"When will the next stage begin?",
"Pending reply"));

panel.add(createQuestionItem(
"Usman Khan - House 08",
"Can I see the latest site photos?",
"Pending reply"));

JButton viewAll = new JButton("View All Questions");
viewAll.setAlignmentX(Component.LEFT_ALIGNMENT);
viewAll.addActionListener(e -> showSection("Homeowner Questions"));

panel.add(Box.createVerticalStrut(10));
panel.add(viewAll);

return panel;
}

private JPanel createQuestionItem(
String homeowner, String question, String status) {

JPanel item = new JPanel();
item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
item.setBackground(Color.WHITE);
item.setAlignmentX(Component.LEFT_ALIGNMENT);
item.setBorder(new EmptyBorder(8, 0, 10, 0));

JLabel homeownerLabel = new JLabel(homeowner);
homeownerLabel.setFont(new Font("Arial", Font.BOLD, 12));
homeownerLabel.setForeground(TEXT);

JLabel questionLabel = new JLabel(
"<html><body style='width:250px'>"
+ question + "</body></html>");
questionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
questionLabel.setForeground(Color.GRAY);

JLabel statusLabel = new JLabel(status);
statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
statusLabel.setForeground(ORANGE);

item.add(homeownerLabel);
item.add(Box.createVerticalStrut(5));
item.add(questionLabel);
item.add(Box.createVerticalStrut(5));
item.add(statusLabel);

return item;
}

private JPanel createWhitePanel() {

JPanel panel = new JPanel();
panel.setBackground(Color.WHITE);
panel.setBorder(BorderFactory.createLineBorder(
new Color(230, 234, 240)));

return panel;
}

private JButton createActionButton(String text) {

JButton button = new JButton(text);
button.setBackground(Color.WHITE);
button.setForeground(BLUE);
button.setFont(new Font("Arial", Font.BOLD, 12));
button.setFocusPainted(false);
button.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(new Color(220, 227, 237)),
new EmptyBorder(12, 8, 12, 8)));
button.setCursor(new Cursor(Cursor.HAND_CURSOR));

button.addActionListener(e -> showSection(text));

return button;
}

private void showSection(String section) {

if (section.equals("Overview")) {
JOptionPane.showMessageDialog(
this, "You are already on the dashboard.");
} else if (section.equals("Logout")) {
dispose();
new LoginForm();
} else {
JOptionPane.showMessageDialog(
this,
section + " page will be added later.",
section,
JOptionPane.INFORMATION_MESSAGE);
}
}

public static void main(String[] args) {

SwingUtilities.invokeLater(() ->
new SupervisorDashboard());
}
}
