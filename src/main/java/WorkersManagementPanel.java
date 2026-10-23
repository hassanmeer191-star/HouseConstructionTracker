
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class WorkersManagementPanel extends JPanel {

    private final Color BLUE = new Color(47, 101, 200);
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color GREEN = new Color(37, 145, 93);

    private final int supervisorId;
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final WorkerDAO workerDAO = new WorkerDAO();

    private JComboBox<ProjectOption> projectCombo;
    private JTextField searchField;
    private JLabel totalLabel;
    private JLabel activeLabel;
    private JLabel excellentLabel;

    private DefaultTableModel tableModel;
    private JTable workerTable;
    private TableRowSorter<DefaultTableModel> sorter;

    private List<Worker> workers = new ArrayList<>();

    public WorkersManagementPanel(int supervisorId) {
        this.supervisorId = supervisorId;

        setLayout(new BorderLayout(15, 18));
        setBackground(BACKGROUND);

        setBorder(new EmptyBorder(5, 5, 5, 5));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadProjects();
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);

        JLabel heading = new JLabel("Workers Management");
        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(TEXT);

        JLabel subtitle = new JLabel(
            "Manage workers, review performance, and record bonuses."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBorder(new EmptyBorder(7, 0, 15, 0));

        JPanel projectRow = new JPanel(new BorderLayout(10, 5));
        projectRow.setBackground(BACKGROUND);

        JLabel projectLabel = new JLabel("Construction Project:");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 13));
        projectLabel.setForeground(TEXT);

        projectCombo = new JComboBox<>();
        projectCombo.setPreferredSize(new Dimension(300, 34));
        projectCombo.setFont(new Font("Arial", Font.PLAIN, 13));

        projectCombo.addActionListener(e -> loadWorkers());

        projectRow.add(projectLabel, BorderLayout.WEST);
        projectRow.add(projectCombo, BorderLayout.CENTER);

        panel.add(heading);
        panel.add(subtitle);
        panel.add(projectRow);

        return panel;
    }

    private JPanel createMainContent() {
        JPanel panel = new JPanel(new BorderLayout(12, 15));
        panel.setBackground(BACKGROUND);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);

        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setBackground(BACKGROUND);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        totalLabel = new JLabel("0");
        activeLabel = new JLabel("0");
        excellentLabel = new JLabel("0");

        stats.add(createStatCard("Total Workers", totalLabel, BLUE));
        stats.add(createStatCard("Active Workers", activeLabel, GREEN));
        stats.add(createStatCard("Excellent Performance",
                                 excellentLabel, new Color(145, 85, 190)));

        JPanel toolbar = new JPanel(new BorderLayout(10, 5));
        toolbar.setBackground(BACKGROUND);
        toolbar.setBorder(new EmptyBorder(15, 0, 10, 0));

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setToolTipText("Search by worker name, phone, or job");

        searchField.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(
                        javax.swing.event.DocumentEvent e) {
                    applySearch();
                }

                @Override
                public void removeUpdate(
                        javax.swing.event.DocumentEvent e) {
                    applySearch();
                }

                @Override
                public void changedUpdate(
                        javax.swing.event.DocumentEvent e) {
                    applySearch();
                }
            }
        );

        JButton addButton = new JButton("+ Add Worker");
        addButton.setBackground(BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(135, 35));
        addButton.addActionListener(e -> addWorker());

        toolbar.add(searchField, BorderLayout.CENTER);
        toolbar.add(addButton, BorderLayout.EAST);

        top.add(stats);
        top.add(toolbar);

        String[] columns = {
            "ID", "Full Name", "Phone", "Job Type",
            "Joining Date", "Wage Type", "Wage Amount",
            "Status", "Performance", "Bonus (PKR)"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        workerTable = new JTable(tableModel);
        workerTable.setRowHeight(32);
        workerTable.setFont(new Font("Arial", Font.PLAIN, 12));
        workerTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        workerTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        workerTable.getTableHeader().setBackground(
            new Color(235, 240, 248)
        );

        sorter = new TableRowSorter<>(tableModel);
        workerTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(workerTable);
        scrollPane.setBorder(
            BorderFactory.createLineBorder(new Color(225, 230, 238))
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel bottom = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 5)
        );
        bottom.setBackground(BACKGROUND);

        JButton editButton = new JButton("Edit Selected Worker");
        JButton refreshButton = new JButton("Refresh");

        editButton.addActionListener(e -> editWorker());
        refreshButton.addActionListener(e -> loadWorkers());

        bottom.add(editButton);
        bottom.add(refreshButton);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatCard(
            String title, JLabel value, Color accent) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 234, 240)),
            new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        value.setFont(new Font("Arial", Font.BOLD, 23));
        value.setForeground(accent);
        value.setBorder(new EmptyBorder(5, 0, 0, 0));

        card.add(titleLabel);
        card.add(value);

        return card;
    }

    // Load only projects assigned to this supervisor.
    private void loadProjects() {
        projectCombo.removeAllItems();

        try {
            List<Map<String, Object>> projects =
                projectDAO.getAllProjects();

            for (Map<String, Object> row : projects) {
                int assignedSupervisor =
                    ((Number) row.get("supervisor_id")).intValue();

                if (assignedSupervisor == supervisorId) {
                    projectCombo.addItem(new ProjectOption(
                        ((Number) row.get("project_id")).intValue(),
                        (String) row.get("project_name")
                    ));
                }
            }

            if (projectCombo.getItemCount() == 0) {
                clearWorkerTable();
                JOptionPane.showMessageDialog(
                    this,
                    "No construction projects are assigned to you.",
                    "No Projects",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {
            showError("Could not load projects:\n" + ex.getMessage());
        }
    }

    // Load workers for the selected project.
    private void loadWorkers() {
        ProjectOption selected =
            (ProjectOption) projectCombo.getSelectedItem();

        if (selected == null || tableModel == null) {
            return;
        }

        try {
            workers = workerDAO.getWorkersByProject(selected.id);
            tableModel.setRowCount(0);

            for (Worker worker : workers) {
                tableModel.addRow(new Object[] {
                    worker.getWorkerId(),
                    worker.getFullName(),
                    worker.getPhone(),
                    worker.getJobType(),
                    worker.getJoiningDate(),
                    worker.getWageType(),
                    worker.getWageAmount(),
                    worker.getWorkerStatus(),
                    worker.getPerformanceRating(),
                    worker.getBonusAmount()
                });
            }

            updateStatistics();
            applySearch();

        } catch (SQLException ex) {
            showError("Could not load workers:\n" + ex.getMessage());
        }
    }

    private void clearWorkerTable() {
        workers.clear();

        if (tableModel != null) {
            tableModel.setRowCount(0);
        }

        updateStatistics();
    }

    private void updateStatistics() {
        int active = 0;
        int excellent = 0;

        for (Worker worker : workers) {
            if ("Active".equals(worker.getWorkerStatus())) {
                active++;
            }

            if ("Excellent".equals(worker.getPerformanceRating())) {
                excellent++;
            }
        }

        totalLabel.setText(String.valueOf(workers.size()));
        activeLabel.setText(String.valueOf(active));
        excellentLabel.setText(String.valueOf(excellent));
    }

    private void applySearch() {
        if (sorter == null || searchField == null) {
            return;
        }

        String search = searchField.getText().trim();

        if (search.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(
                "(?i)" + java.util.regex.Pattern.quote(search),
                1, 2, 3
            ));
        }
    }

    private void addWorker() {
        ProjectOption project =
            (ProjectOption) projectCombo.getSelectedItem();

        if (project == null) {
            showError("Please select a construction project first.");
            return;
        }

        Worker worker = showWorkerForm(null);

        if (worker == null) {
            return;
        }

        try {
            workerDAO.addWorker(worker);
            loadWorkers();

            JOptionPane.showMessageDialog(
                this,
                "Worker added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            showError("Could not add worker:\n" + ex.getMessage());
        }
    }

    private void editWorker() {
        int selectedRow = workerTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a worker from the table.");
            return;
        }

        int modelRow = workerTable.convertRowIndexToModel(selectedRow);
        int workerId = ((Number)
            tableModel.getValueAt(modelRow, 0)).intValue();

        Worker selectedWorker = null;

        for (Worker worker : workers) {
            if (worker.getWorkerId() == workerId) {
                selectedWorker = worker;
                break;
            }
        }

        if (selectedWorker == null) {
            showError("Could not find the selected worker.");
            return;
        }

        Worker edited = showWorkerForm(selectedWorker);

        if (edited == null) {
            return;
        }

        try {
            workerDAO.updateWorker(
                edited.getWorkerId(),
                edited.getProjectId(),
                edited.getFullName(),
                edited.getPhone(),
                edited.getJobType(),
                edited.getJoiningDate(),
                edited.getWageType(),
                edited.getWageAmount(),
                edited.getWorkerStatus(),
                edited.getPerformanceRating(),
                edited.getPerformanceNotes(),
                edited.getBonusAmount()
            );

            loadWorkers();

            JOptionPane.showMessageDialog(
                this,
                "Worker details updated successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            showError("Could not update worker:\n" + ex.getMessage());
        }
    }

    private Worker showWorkerForm(Worker existing) {
        boolean editing = existing != null;

        ProjectOption project =
            (ProjectOption) projectCombo.getSelectedItem();

        if (project == null) {
            return null;
        }

        JTextField nameField = new JTextField(
            editing ? existing.getFullName() : ""
        );

        JTextField phoneField = new JTextField(
            editing && existing.getPhone() != null
                ? existing.getPhone() : ""
        );

        JComboBox<String> jobField = new JComboBox<>(new String[] {
            "Mason", "Laborer", "Electrician", "Plumber",
            "Carpenter", "Painter", "Other"
        });

        JComboBox<String> wageTypeField = new JComboBox<>(
            new String[] {"Daily", "Hourly"}
        );

        JTextField wageField = new JTextField(
            editing ? existing.getWageAmount().toPlainString() : ""
        );

        JTextField dateField = new JTextField(
            editing ? existing.getJoiningDate().toString()
                    : LocalDate.now().toString()
        );

        JComboBox<String> statusField = new JComboBox<>(
            new String[] {"Active", "Inactive"}
        );

        JComboBox<String> ratingField = new JComboBox<>(
            new String[] {
                "Excellent", "Good", "Average", "Needs Improvement"
            }
        );

        JTextField bonusField = new JTextField(
            editing ? existing.getBonusAmount().toPlainString() : "0"
        );

        JTextArea notesArea = new JTextArea(
            editing && existing.getPerformanceNotes() != null
                ? existing.getPerformanceNotes() : "",
            3, 20
        );
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        if (editing) {
            jobField.setSelectedItem(existing.getJobType());
            wageTypeField.setSelectedItem(existing.getWageType());
            statusField.setSelectedItem(existing.getWorkerStatus());
            ratingField.setSelectedItem(
                existing.getPerformanceRating()
            );
        }

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setPreferredSize(new Dimension(440, 400));

        form.add(new JLabel("Full Name:"));
        form.add(nameField);

        form.add(new JLabel("Phone Number:"));
        form.add(phoneField);

        form.add(new JLabel("Job Type:"));
        form.add(jobField);

        form.add(new JLabel("Joining Date (YYYY-MM-DD):"));
        form.add(dateField);

        form.add(new JLabel("Wage Type:"));
        form.add(wageTypeField);

        form.add(new JLabel("Wage Amount (PKR):"));
        form.add(wageField);

        form.add(new JLabel("Worker Status:"));
        form.add(statusField);

        form.add(new JLabel("Performance Rating:"));
        form.add(ratingField);

        form.add(new JLabel("Bonus Amount (PKR):"));
        form.add(bonusField);

        form.add(new JLabel("Performance Notes:"));
        form.add(new JScrollPane(notesArea));

        if (editing) {
            jobField.setSelectedItem(existing.getJobType());
        }

        int result = JOptionPane.showConfirmDialog(
            this,
            form,
            editing ? "Edit Worker" : "Add Worker",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String job = (String) jobField.getSelectedItem();
        String wageType = (String) wageTypeField.getSelectedItem();
        String status = (String) statusField.getSelectedItem();
        String rating = (String) ratingField.getSelectedItem();
        String dateText = dateField.getText().trim();

        if (name.isEmpty() || dateText.isEmpty()
                || wageField.getText().trim().isEmpty()
                || bonusField.getText().trim().isEmpty()) {
            showError("Please fill in the required fields.");
            return null;
        }

        try {
            LocalDate joiningDate = LocalDate.parse(dateText);
            BigDecimal wage = new BigDecimal(
                wageField.getText().trim()
            );
            BigDecimal bonus = new BigDecimal(
                bonusField.getText().trim()
            );

            if (wage.compareTo(BigDecimal.ZERO) < 0
                    || bonus.compareTo(BigDecimal.ZERO) < 0) {
                showError("Wage and bonus cannot be negative.");
                return null;
            }

            Worker worker = new Worker(
                project.id,
                name,
                phone,
                job,
                joiningDate,
                wageType,
                wage
            );

            worker.setWorkerStatus(status);
            worker.setPerformanceRating(rating);
            worker.setPerformanceNotes(notesArea.getText().trim());
            worker.setBonusAmount(bonus);

            if (editing) {
                worker.setWorkerId(existing.getWorkerId());
            }

            return worker;

        } catch (java.time.format.DateTimeParseException ex) {
            showError("Enter the joining date as YYYY-MM-DD.");
        } catch (NumberFormatException ex) {
            showError("Enter valid numbers for wage and bonus.");
        }

        return null;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Workers Management",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private static class ProjectOption {
        private final int id;
        private final String name;

        ProjectOption(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }
}