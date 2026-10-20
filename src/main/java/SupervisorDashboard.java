
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SupervisorDashboard extends JFrame {

private final Color SIDEBAR = new Color(23, 43, 77);
private final Color BLUE = new Color(47, 101, 200);
private final Color BACKGROUND = new Color(245, 247, 251);
private final Color TEXT = new Color(45, 55, 72);
private final Color GREEN = new Color(37, 145, 93);
private final Color ORANGE = new Color(220, 145, 42);

private JPanel contentPanel;
private JLabel pageLabel;
private JPanel sidebarPanel;

private final List<ProjectInfo> projects = new ArrayList<>();
private final ProjectDAO projectDAO = new ProjectDAO();

// Temporary test ID. Connect this to LoginForm later.
private final int currentSupervisorId = 1;

private DefaultTableModel projectTableModel;
private JTable projectTable;
private TableRowSorter<DefaultTableModel> projectSorter;
private JTextField projectSearchField;
private JComboBox<String> statusFilter;

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
sidebarPanel = new JPanel();
sidebarPanel.setPreferredSize(new Dimension(235, 0));
sidebarPanel.setBackground(SIDEBAR);
sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
sidebarPanel.setBorder(new EmptyBorder(22, 14, 18, 14));

JLabel logo = new JLabel("  BuildTrack");
logo.setFont(new Font("Arial", Font.BOLD, 25));
logo.setForeground(Color.WHITE);
logo.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel workspace = new JLabel("SUPERVISOR WORKSPACE");
workspace.setFont(new Font("Arial", Font.BOLD, 10));
workspace.setForeground(new Color(170, 190, 220));
workspace.setBorder(new EmptyBorder(8, 10, 25, 0));
workspace.setAlignmentX(Component.LEFT_ALIGNMENT);

sidebarPanel.add(logo);
sidebarPanel.add(workspace);

String[] menuItems = {
"Overview", "Construction Projects", "Construction Stages",
"Progress Updates", "Daily Work Status", "Homeowner Questions",
"Budget and Expenses", "Workers Management", "Site Photos",
"Reports and History", "Settings"
};

for (String item : menuItems) {
JButton button = createMenuButton(item);

if (item.equals("Overview")) {
button.setBackground(BLUE);
button.setForeground(Color.WHITE);
}

button.addActionListener(e -> showSection(item));
sidebarPanel.add(button);
sidebarPanel.add(Box.createVerticalStrut(5));
}

sidebarPanel.add(Box.createVerticalGlue());

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

sidebarPanel.add(profile);
sidebarPanel.add(logoutButton);
return sidebarPanel;
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

pageLabel = new JLabel("Workspace / Overview");
pageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
pageLabel.setForeground(new Color(110, 120, 135));

JPanel rightControls = new JPanel(
new FlowLayout(FlowLayout.RIGHT, 15, 0));
rightControls.setBackground(Color.WHITE);

JTextField searchField = new JTextField("Search projects...");
searchField.setPreferredSize(new Dimension(180, 32));
searchField.setForeground(Color.GRAY);

JButton notificationButton = new JButton("Notifications");
notificationButton.setFocusPainted(false);
notificationButton.addActionListener(e ->
JOptionPane.showMessageDialog(
this,
"Notifications will be connected later.",
"Notifications",
JOptionPane.INFORMATION_MESSAGE));

rightControls.add(searchField);
rightControls.add(notificationButton);
topBar.add(pageLabel, BorderLayout.WEST);
topBar.add(rightControls, BorderLayout.EAST);
return topBar;
}

// ---------------- OVERVIEW ----------------

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

stats.add(createStatCard("Active Projects", "3",
"Currently managed", BLUE));
stats.add(createStatCard("Average Progress", "65%",
"Across all projects", GREEN));
stats.add(createStatCard("Pending Questions", "4",
"Need a reply", ORANGE));
stats.add(createStatCard("Today's Work", "In Progress",
"Site activity", BLUE));

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

JPanel rows = new JPanel(new GridLayout(3, 1, 0, 8));
rows.setBackground(Color.WHITE);
rows.add(createProjectRow("House 12 - Bahria Town",
"Homeowner: Ali Ahmed", 65, "On Track", GREEN));
rows.add(createProjectRow("House 08 - DHA Phase 2",
"Homeowner: Usman Khan", 42, "Needs Attention", ORANGE));
rows.add(createProjectRow("House 21 - Gulraiz Housing",
"Homeowner: Ahmed Raza", 88, "Near Completion", BLUE));

panel.add(rows, BorderLayout.CENTER);
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
panel.add(createActivityItem("Progress updated",
"House 12 progress changed to 65%", "Today"));
panel.add(createActivityItem("New homeowner question",
"A question was received", "Today"));
panel.add(createActivityItem("Site photo uploaded",
"New construction photo added", "Yesterday"));
panel.add(createActivityItem("Daily status updated",
"Work marked as in progress", "Yesterday"));
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

panel.add(createQuestionItem("Ali Ahmed - House 12",
"When will the next stage begin?", "Pending reply"));
panel.add(createQuestionItem("Usman Khan - House 08",
"Can I see the latest site photos?", "Pending reply"));

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

// ---------------- CONSTRUCTION PROJECTS ----------------

private JPanel createConstructionProjectsPage() {
JPanel page = new JPanel(new BorderLayout(15, 18));
page.setBackground(BACKGROUND);

JPanel header = new JPanel(new BorderLayout());
header.setBackground(BACKGROUND);

JPanel titlePanel = new JPanel();
titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
titlePanel.setBackground(BACKGROUND);

JLabel heading = new JLabel("Construction Projects");
heading.setFont(new Font("Arial", Font.BOLD, 27));
heading.setForeground(TEXT);

JLabel subtitle = new JLabel(
"View and manage the houses assigned to you.");
subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
subtitle.setForeground(Color.GRAY);
subtitle.setBorder(new EmptyBorder(7, 0, 0, 0));

titlePanel.add(heading);
titlePanel.add(subtitle);

JButton addButton = new JButton("+ Add Project");
addButton.setBackground(BLUE);
addButton.setForeground(Color.WHITE);
addButton.setFont(new Font("Arial", Font.BOLD, 13));
addButton.setFocusPainted(false);
addButton.addActionListener(e -> addProject());

header.add(titlePanel, BorderLayout.WEST);
header.add(addButton, BorderLayout.EAST);
page.add(header, BorderLayout.NORTH);

JPanel filterPanel = new JPanel(
new FlowLayout(FlowLayout.LEFT, 10, 5));
filterPanel.setBackground(BACKGROUND);

projectSearchField = new JTextField(23);
projectSearchField.setPreferredSize(new Dimension(220, 34));

JButton searchButton = new JButton("Search");
searchButton.addActionListener(e -> applyProjectFilters());

JButton clearButton = new JButton("Clear");
clearButton.addActionListener(e -> {
projectSearchField.setText("");
statusFilter.setSelectedItem("All Statuses");
applyProjectFilters();
});

statusFilter = new JComboBox<>(new String[]{
"All Statuses", "Planned", "In Progress", "On Hold", "Completed"
});
statusFilter.addActionListener(e -> applyProjectFilters());

filterPanel.add(new JLabel("Search:"));
filterPanel.add(projectSearchField);
filterPanel.add(searchButton);
filterPanel.add(new JLabel("Status:"));
filterPanel.add(statusFilter);
filterPanel.add(clearButton);

String[] columns = {
"Project ID", "Project Name", "Homeowner", "Location",
"Total Budget", "Amount Spent", "Progress", "Status"
};

projectTableModel = new DefaultTableModel(columns, 0) {
@Override
public boolean isCellEditable(int row, int column) {
return false;
}
};

projectTable = new JTable(projectTableModel);
projectTable.setRowHeight(32);
projectTable.setFont(new Font("Arial", Font.PLAIN, 12));
projectTable.getTableHeader().setFont(
new Font("Arial", Font.BOLD, 12));
projectTable.getTableHeader().setBackground(
new Color(235, 240, 248));
projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
projectTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

int[] widths = {90, 200, 130, 200, 120, 120, 85, 110};
for (int i = 0; i < widths.length; i++) {
projectTable.getColumnModel().getColumn(i)
.setPreferredWidth(widths[i]);
}

projectSorter = new TableRowSorter<>(projectTableModel);
projectTable.setRowSorter(projectSorter);

JScrollPane tableScroll = new JScrollPane(projectTable);
tableScroll.setBorder(BorderFactory.createLineBorder(
new Color(225, 230, 238)));
tableScroll.getViewport().setBackground(Color.WHITE);

JPanel actions = new JPanel(
new FlowLayout(FlowLayout.LEFT, 10, 5));
actions.setBackground(BACKGROUND);

JButton detailsButton = new JButton("View Details");
JButton editButton = new JButton("Edit Project");
JButton refreshButton = new JButton("Refresh Table");

detailsButton.addActionListener(e -> viewProjectDetails());
editButton.addActionListener(e -> editProject());
refreshButton.addActionListener(e -> refreshProjectTable());

actions.add(detailsButton);
actions.add(editButton);
actions.add(refreshButton);

JPanel center = new JPanel(new BorderLayout(0, 12));
center.setBackground(BACKGROUND);
center.add(filterPanel, BorderLayout.NORTH);
center.add(tableScroll, BorderLayout.CENTER);
center.add(actions, BorderLayout.SOUTH);

page.add(center, BorderLayout.CENTER);
refreshProjectTable();
return page;
}

private void refreshProjectTable() {
if (projectTableModel == null) {
return;
}

try {
List<Map<String, Object>> databaseProjects =
projectDAO.getAllProjects();

projects.clear();

for (Map<String, Object> row : databaseProjects) {
projects.add(new ProjectInfo(
((Number) row.get("project_id")).intValue(),
(String) row.get("project_name"),
(String) row.get("homeowner_name"),
((Number) row.get("homeowner_id")).intValue(),
((Number) row.get("supervisor_id")).intValue(),
(String) row.get("location"),
((BigDecimal) row.get("total_budget")).doubleValue(),
((BigDecimal) row.get("amount_spent")).doubleValue(),
((Number) row.get("progress_percent")).intValue(),
(String) row.get("status")
));
}

projectTableModel.setRowCount(0);

for (ProjectInfo project : projects) {
projectTableModel.addRow(new Object[]{
project.id, project.name, project.homeowner,
project.location, formatMoney(project.totalBudget),
formatMoney(project.amountSpent),
project.progress + "%", project.status
});
}

applyProjectFilters();

} catch (SQLException ex) {
showError("Could not load projects from Neon:\n" + ex.getMessage());
}
}

private void applyProjectFilters() {
if (projectSorter == null || projectSearchField == null
|| statusFilter == null) {
return;
}

String search = projectSearchField.getText().trim();
String selectedStatus = (String) statusFilter.getSelectedItem();

List<RowFilter<Object, Object>> filters = new ArrayList<>();

if (!search.isEmpty()) {
filters.add(RowFilter.regexFilter(
"(?i)" + java.util.regex.Pattern.quote(search)));
}

if (selectedStatus != null
&& !selectedStatus.equals("All Statuses")) {
filters.add(RowFilter.regexFilter(
"^" + java.util.regex.Pattern.quote(selectedStatus) + "$",
7));
}

projectSorter.setRowFilter(filters.isEmpty()
? null : RowFilter.andFilter(filters));
}

private void addProject() {
JTextField nameField = new JTextField();
JTextField homeownerField = new JTextField();
JTextField locationField = new JTextField();
JTextField budgetField = new JTextField();

Object[] fields = {
"Project Name:", nameField,
"Homeowner Name:", homeownerField,
"Location:", locationField,
"Total Budget (PKR):", budgetField
};

int result = JOptionPane.showConfirmDialog(
this, fields, "Add Construction Project",
JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (result != JOptionPane.OK_OPTION) {
return;
}

String name = nameField.getText().trim();
String homeowner = homeownerField.getText().trim();
String location = locationField.getText().trim();
String budgetText = budgetField.getText().trim();

if (name.isEmpty() || homeowner.isEmpty()
|| location.isEmpty() || budgetText.isEmpty()) {
showError("Please fill in all project fields.");
return;
}

try {
BigDecimal budget = new BigDecimal(budgetText);

if (budget.compareTo(BigDecimal.ZERO) <= 0) {
showError("Budget must be greater than zero.");
return;
}

int homeownerId = projectDAO.getHomeownerIdByName(homeowner);

int projectId = projectDAO.addProject(
name, location, homeownerId,
currentSupervisorId, budget);

refreshProjectTable();

JOptionPane.showMessageDialog(
this, "Project saved successfully.\nProject ID: "
+ projectId, "Project Added",
JOptionPane.INFORMATION_MESSAGE);

} catch (NumberFormatException ex) {
showError("Please enter a valid budget.");
} catch (SQLException ex) {
showError("Could not save project:\n" + ex.getMessage());
}
}

private void editProject() {
ProjectInfo project = getSelectedProject();

if (project == null) {
return;
}

if (project.supervisorId != currentSupervisorId) {
showError("You can only edit projects assigned to you.");
return;
}

JTextField nameField = new JTextField(project.name);
JTextField homeownerField = new JTextField(project.homeowner);
JTextField locationField = new JTextField(project.location);
JTextField budgetField =
new JTextField(String.valueOf(project.totalBudget));
JTextField spentField =
new JTextField(String.valueOf(project.amountSpent));

JSpinner progressField = new JSpinner(
new SpinnerNumberModel(project.progress, 0, 100, 1));

JComboBox<String> statusField = new JComboBox<>(new String[]{
"Planned", "In Progress", "On Hold", "Completed"
});
statusField.setSelectedItem(project.status);

Object[] fields = {
"Project Name:", nameField,
"Homeowner Name:", homeownerField,
"Location:", locationField,
"Total Budget (PKR):", budgetField,
"Amount Spent (PKR):", spentField,
"Progress (%):", progressField,
"Status:", statusField
};

int result = JOptionPane.showConfirmDialog(
this, fields, "Edit Project",
JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (result != JOptionPane.OK_OPTION) {
return;
}

String name = nameField.getText().trim();
String homeowner = homeownerField.getText().trim();
String location = locationField.getText().trim();

if (name.isEmpty() || homeowner.isEmpty() || location.isEmpty()) {
showError("Please fill in the required fields.");
return;
}

try {
BigDecimal budget = new BigDecimal(budgetField.getText().trim());
BigDecimal spent = new BigDecimal(spentField.getText().trim());

if (budget.compareTo(BigDecimal.ZERO) <= 0
|| spent.compareTo(BigDecimal.ZERO) < 0) {
showError("Budget must be positive and spending cannot be negative.");
return;
}

if (spent.compareTo(budget) > 0) {
int choice = JOptionPane.showConfirmDialog(
this,
"Amount spent is greater than the total budget. Continue?",
"Budget Warning",
JOptionPane.YES_NO_OPTION,
JOptionPane.WARNING_MESSAGE);

if (choice != JOptionPane.YES_OPTION) {
return;
}
}

int homeownerId = projectDAO.getHomeownerIdByName(homeowner);

projectDAO.updateProject(
project.id,
name,
location,
homeownerId,
budget,
spent,
(Integer) progressField.getValue(),
(String) statusField.getSelectedItem(),
currentSupervisorId);

refreshProjectTable();

JOptionPane.showMessageDialog(
this, "Project updated successfully.",
"Project Updated", JOptionPane.INFORMATION_MESSAGE);

} catch (NumberFormatException ex) {
showError("Please enter valid numbers for budget and spending.");
} catch (SQLException ex) {
showError("Could not update project:\n" + ex.getMessage());
}
}

private ProjectInfo getSelectedProject() {
int selectedRow = projectTable.getSelectedRow();

if (selectedRow == -1) {
showError("Please select a project from the table first.");
return null;
}

int modelRow = projectTable.convertRowIndexToModel(selectedRow);
int id = ((Number) projectTableModel.getValueAt(modelRow, 0)).intValue();

for (ProjectInfo project : projects) {
if (project.id == id) {
return project;
}
}

showError("The selected project could not be found.");
return null;
}

private void viewProjectDetails() {
ProjectInfo project = getSelectedProject();

if (project == null) {
return;
}

double remaining = project.totalBudget - project.amountSpent;

String details = "<html><div style='width:340px;'>"
+ "<h2>" + escapeHtml(project.name) + "</h2>"
+ "<b>Project ID:</b> " + project.id + "<br><br>"
+ "<b>Homeowner:</b> " + escapeHtml(project.homeowner) + "<br>"
+ "<b>Location:</b> " + escapeHtml(project.location) + "<br><br>"
+ "<b>Total Budget:</b> " + formatMoney(project.totalBudget) + "<br>"
+ "<b>Amount Spent:</b> " + formatMoney(project.amountSpent) + "<br>"
+ "<b>Remaining Budget:</b> " + formatMoney(remaining) + "<br><br>"
+ "<b>Progress:</b> " + project.progress + "%<br>"
+ "<b>Status:</b> " + escapeHtml(project.status)
+ "</div></html>";

JOptionPane.showMessageDialog(
this, details, "Project Details",
JOptionPane.INFORMATION_MESSAGE);
}

private String formatMoney(double amount) {
return String.format("PKR %,.2f", amount);
}

private void showError(String message) {
JOptionPane.showMessageDialog(
this, message, "Check Information",
JOptionPane.WARNING_MESSAGE);
}

private String escapeHtml(String text) {
return text.replace("&", "&amp;")
.replace("<", "&lt;")
.replace(">", "&gt;")
.replace("\"", "&quot;")
.replace("'", "&#39;");
}

private static class ProjectInfo {
int id;
String name;
String homeowner;
int homeownerId;
int supervisorId;
String location;
double totalBudget;
double amountSpent;
int progress;
String status;

ProjectInfo(
int id, String name, String homeowner,
int homeownerId, int supervisorId, String location,
double totalBudget, double amountSpent,
int progress, String status) {
this.id = id;
this.name = name;
this.homeowner = homeowner;
this.homeownerId = homeownerId;
this.supervisorId = supervisorId;
this.location = location;
this.totalBudget = totalBudget;
this.amountSpent = amountSpent;
this.progress = progress;
this.status = status;
}
}

// ---------------- NAVIGATION ----------------


private JPanel createSitePhotosPage() {
JPanel page = new JPanel(new BorderLayout(15, 18));
page.setBackground(BACKGROUND);

JPanel header = new JPanel();
header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
header.setBackground(BACKGROUND);

JLabel heading = new JLabel("Site Photos");
heading.setFont(new Font("Arial", Font.BOLD, 27));
heading.setForeground(TEXT);

JLabel subtitle = new JLabel(
"Select a construction project to upload its site photos."
);
subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
subtitle.setForeground(Color.GRAY);
subtitle.setBorder(new EmptyBorder(7, 0, 10, 0));

header.add(heading);
header.add(subtitle);
page.add(header, BorderLayout.NORTH);

String[] columns = {
"Project ID", "Project Name", "Homeowner",
"Location", "Status"
};

DefaultTableModel model = new DefaultTableModel(columns, 0) {
@Override
public boolean isCellEditable(int row, int column) {
return false;
}
};

JTable table = new JTable(model);
table.setRowHeight(32);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Load projects directly from Neon.
try {
List<Map<String, Object>> databaseProjects =
projectDAO.getAllProjects();

for (Map<String, Object> row : databaseProjects) {
int supervisorId =
((Number) row.get("supervisor_id")).intValue();

// Only show projects assigned to this supervisor.
if (supervisorId != currentSupervisorId) {
continue;
}

model.addRow(new Object[]{
row.get("project_id"),
row.get("project_name"),
row.get("homeowner_name"),
row.get("location"),
row.get("status")
});
}

} catch (SQLException ex) {
showError(
"Could not load projects:\n" + ex.getMessage()
);
}

JScrollPane scrollPane = new JScrollPane(table);
scrollPane.getViewport().setBackground(Color.WHITE);
page.add(scrollPane, BorderLayout.CENTER);

JButton manageButton = new JButton("Manage Photos");
manageButton.setBackground(BLUE);
manageButton.setForeground(Color.WHITE);
manageButton.setFocusPainted(false);

manageButton.addActionListener(e -> {
int selectedRow = table.getSelectedRow();

if (selectedRow == -1) {
showError("Please select a project first.");
return;
}

int projectId = ((Number) model.getValueAt(
selectedRow, 0)).intValue();

contentPanel.removeAll();
pageLabel.setText("Workspace / Site Photos / Upload");

contentPanel.add(
new SitePhotosPanel(
    projectId,
    currentSupervisorId
),
BorderLayout.CENTER
);

contentPanel.revalidate();
contentPanel.repaint();
});

JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
bottom.setBackground(BACKGROUND);
bottom.add(manageButton);

page.add(bottom, BorderLayout.SOUTH);

return page;
}


private void showSection(String section) {
contentPanel.removeAll();

if (section.equals("Overview")) {
pageLabel.setText("Workspace / Overview");
contentPanel.add(
createDashboardContent(),
BorderLayout.CENTER
);

} else if (section.equals("Construction Projects")) {
pageLabel.setText("Workspace / Construction Projects");
contentPanel.add(
createConstructionProjectsPage(),
BorderLayout.CENTER
);

} else if (section.equals("Site Photos")) {
pageLabel.setText("Workspace / Site Photos");
contentPanel.add(
createSitePhotosPage(),
BorderLayout.CENTER
);

} else if (section.equals("Logout")) {
dispose();
new LoginForm();
return;

} else {
pageLabel.setText("Workspace / " + section);

JPanel placeholder = new JPanel(new GridBagLayout());
placeholder.setBackground(BACKGROUND);

JLabel message = new JLabel(
section + " page will be added later."
);
message.setFont(new Font("Arial", Font.BOLD, 18));
message.setForeground(TEXT);

placeholder.add(message);
contentPanel.add(placeholder, BorderLayout.CENTER);
}

contentPanel.revalidate();
contentPanel.repaint();
}



public static void main(String[] args) {
SwingUtilities.invokeLater(SupervisorDashboard::new);
}
}
