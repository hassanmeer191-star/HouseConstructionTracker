import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HomeownerDashboard extends JFrame {

    // ===== Colors (same as SupervisorDashboard) =====
    private static final Color NAVY = new Color(0x17, 0x2B, 0x4D);
    private static final Color BLUE = new Color(0x2F, 0x65, 0xC8);
    private static final Color BG = new Color(0xF5, 0xF7, 0xFB);
    private static final Color GREEN = new Color(0x25, 0x91, 0x5D);
    private static final Color ORANGE = new Color(0xDC, 0x91, 0x2A);
    private static final Color GRAY = new Color(0x6B, 0x77, 0x85);
    private static final Color TEXT = new Color(0x1F, 0x2A, 0x37);

    // ===== TEST DATA (hardcoded - will come from SQL Server later) =====
    private static final String[] STAGE_NAMES = {
            "Planning", "Excavation", "Foundation", "Structure",
            "Brickwork", "Roofing", "Electrical & Plumbing", "Finishing"
    };
    private static final int[] STAGE_PROGRESS = { 100, 100, 100, 85, 40, 0, 0, 0 };
    private static final String[] STAGE_STATUS = {
            "Completed", "Completed", "Completed", "In Progress",
            "In Progress", "Not Started", "Not Started", "Not Started"
    };
    private static final long TOTAL_BUDGET = 5000000;
    private static final Object[][] EXPENSES = {
            { "Cement and steel", "Materials", 450000L, "2026-09-02" },
            { "Foundation labour", "Labour", 300000L, "2026-09-10" },
            { "Bricks", "Materials", 250000L, "2026-09-18" },
            { "Transport of sand", "Transport", 50000L, "2026-09-20" }
    };

    // ===== Fields =====
    private String homeownerName;
    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private JLabel pageTitle = new JLabel("Overview");
    private JPanel questionsList;
    private JLabel pendingValueLabel;
    private int pendingCount = 0;

    private final String[] MENU = {
            "Overview", "Construction Stages", "Progress History", "Work Status",
            "Budget", "Site Photos", "Ask Questions", "Settings", "Logout"
    };

    public HomeownerDashboard() {
        this("Homeowner");
    }

    public HomeownerDashboard(String name) {
        this.homeownerName = name;

        setTitle("BuildTrack - Homeowner Dashboard");
        setSize(1200, 720);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        right.add(createTopBar(), BorderLayout.NORTH);

        contentPanel.setBackground(BG);
        contentPanel.add(wrapScroll(createOverviewPage()), "Overview");
        contentPanel.add(wrapScroll(createStagesPage()), "Construction Stages");
        contentPanel.add(wrapScroll(createBudgetPage()), "Budget");
        contentPanel.add(wrapScroll(createQuestionsPage()), "Ask Questions");
        contentPanel.add(createPlaceholderPage("Progress History"), "Progress History");
        contentPanel.add(createPlaceholderPage("Work Status"), "Work Status");
        contentPanel.add(createPlaceholderPage("Site Photos"), "Site Photos");
        contentPanel.add(createPlaceholderPage("Settings"), "Settings");
        right.add(contentPanel, BorderLayout.CENTER);

        add(right, BorderLayout.CENTER);
    }

    // =====================================================
    // SIDEBAR AND TOP BAR
    // =====================================================
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 12));

        JLabel brand = new JLabel("BuildTrack");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 24));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, 8, 4, 0));
        sidebar.add(brand);

        JLabel role = new JLabel("Homeowner Portal");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        role.setForeground(new Color(0xA9, 0xB7, 0xD0));
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        role.setBorder(BorderFactory.createEmptyBorder(0, 8, 24, 0));
        sidebar.add(role);

        for (String item : MENU) {
            sidebar.add(createMenuButton(item));
            sidebar.add(Box.createVerticalStrut(4));
        }
        return sidebar;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(NAVY);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BLUE);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(NAVY);
            }
        });

        btn.addActionListener(e -> showSection(text));
        return btn;
    }

    private void showSection(String name) {
        if (name.equals("Logout")) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
              //  new LoginForm().setVisible(true); // your existing login form
            }
            return;
        }
        pageTitle.setText(name);
        cardLayout.show(contentPanel, name);
    }

    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.WHITE);
        bar.setPreferredSize(new Dimension(0, 64));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE3, 0xE8, 0xF0)),
                BorderFactory.createEmptyBorder(0, 24, 0, 24)));

        JLabel crumb = new JLabel("Workspace  /  ");
        crumb.setForeground(GRAY);
        crumb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pageTitle.setForeground(TEXT);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        left.setOpaque(false);
        left.add(crumb);
        left.add(pageTitle);

        JLabel user = new JLabel("Welcome, " + homeownerName);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        user.setForeground(GRAY);

        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 22));
        rightSide.setOpaque(false);
        rightSide.add(user);

        bar.add(left, BorderLayout.WEST);
        bar.add(rightSide, BorderLayout.EAST);
        return bar;
    }

    // =====================================================
    // PAGE 1: OVERVIEW
    // =====================================================
    private JPanel createOverviewPage() {
        JPanel page = createPageBox();

        page.add(createHeading("My Construction Project",
                "House 12 - Bahria Town (test data)"));
        page.add(Box.createVerticalStrut(16));

        // overall progress = average of the stage percentages
        int sum = 0;
        for (int p : STAGE_PROGRESS)
            sum += p;
        int overall = sum / STAGE_PROGRESS.length;

        long spent = totalSpent();
        long remaining = TOTAL_BUDGET - spent;

        // stat cards
        JPanel cards = new JPanel(new GridLayout(1, 4, 16, 0));
        cards.setOpaque(false);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);
        cards.add(createStatCard("Overall Progress", overall + "%", BLUE));
        cards.add(createStatCard("Current Stage", "Brickwork", ORANGE));
        cards.add(createStatCard("Budget Remaining", money(remaining), GREEN));
        pendingValueLabel = new JLabel("0");
        cards.add(createStatCardWithLabel("Pending Questions", pendingValueLabel, ORANGE));
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        page.add(cards);
        page.add(Box.createVerticalStrut(16));

        // progress panel
        JPanel progressBody = new JPanel();
        progressBody.setLayout(new BoxLayout(progressBody, BoxLayout.Y_AXIS));
        progressBody.setOpaque(false);
        progressBody.add(createProgressRow("Whole House", overall, "On Track"));
        progressBody.add(Box.createVerticalStrut(8));
        progressBody.add(createProgressRow("Current Stage: Brickwork", 40, "In Progress"));
        progressBody.add(Box.createVerticalStrut(8));
        JLabel work = new JLabel("Today's work status: In Progress");
        work.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        work.setForeground(GRAY);
        progressBody.add(work);
        page.add(createCard("Project Progress", progressBody));
        page.add(Box.createVerticalStrut(16));

        // recent updates
        JPanel updates = new JPanel();
        updates.setLayout(new BoxLayout(updates, BoxLayout.Y_AXIS));
        updates.setOpaque(false);
        updates.add(createUpdateItem("Brickwork updated from 30% to 40%", "Today, 10:30 AM"));
        updates.add(createUpdateItem("Supervisor answered your question", "Yesterday, 4:15 PM"));
        updates.add(createUpdateItem("New site photos uploaded", "2 days ago"));
        updates.add(createUpdateItem("Structure stage reached 85%", "5 days ago"));
        page.add(createCard("Recent Updates", updates));

        // set the initial pending count after the questions page exists
        return wrapTop(page);
    }

    // =====================================================
    // PAGE 2: CONSTRUCTION STAGES
    // =====================================================
    private JPanel createStagesPage() {
        JPanel page = createPageBox();
        page.add(createHeading("Construction Stages",
                "Read-only view. Only the supervisor can change progress."));
        page.add(Box.createVerticalStrut(16));

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        for (int i = 0; i < STAGE_NAMES.length; i++) {
            body.add(createProgressRow(STAGE_NAMES[i], STAGE_PROGRESS[i], STAGE_STATUS[i]));
            body.add(Box.createVerticalStrut(12));
        }
        page.add(createCard("All Stages", body));
        return wrapTop(page);
    }

    // =====================================================
    // PAGE 3: BUDGET
    // =====================================================
    private JPanel createBudgetPage() {
        JPanel page = createPageBox();
        page.add(createHeading("Budget and Expenses",
                "Remaining Budget = Total Budget - Total Recorded Expenses"));
        page.add(Box.createVerticalStrut(16));

        long spent = totalSpent();
        long remaining = TOTAL_BUDGET - spent;

        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 0));
        cards.setOpaque(false);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);
        cards.add(createStatCard("Total Budget", money(TOTAL_BUDGET), BLUE));
        cards.add(createStatCard("Total Spent", money(spent), ORANGE));
        cards.add(createStatCard("Remaining", money(remaining), GREEN));
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        page.add(cards);
        page.add(Box.createVerticalStrut(16));

        String[] cols = { "Description", "Category", "Amount (Rs.)", "Date" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            } // read-only
        };
        for (Object[] row : EXPENSES)
            model.addRow(row);

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(600, 200));
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(0xE3, 0xE8, 0xF0)));

        page.add(createCard("Recorded Expenses", tableScroll));
        return wrapTop(page);
    }

    // =====================================================
    // PAGE 4: ASK QUESTIONS
    // =====================================================
    private JPanel createQuestionsPage() {
        JPanel page = createPageBox();
        page.add(createHeading("Questions for the Supervisor",
                "Ask about your project and read the answers here."));
        page.add(Box.createVerticalStrut(16));

        // ask form
        JPanel form = new JPanel(new BorderLayout(0, 10));
        form.setOpaque(false);
        JTextArea input = new JTextArea(4, 40);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        input.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setBorder(BorderFactory.createLineBorder(new Color(0xD5, 0xDB, 0xE5)));

        JButton submit = createActionButton("Submit Question");
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setOpaque(false);
        btnRow.add(submit);

        form.add(inputScroll, BorderLayout.CENTER);
        form.add(btnRow, BorderLayout.SOUTH);
        page.add(createCard("Ask a New Question", form));
        page.add(Box.createVerticalStrut(16));

        // previous questions
        questionsList = new JPanel();
        questionsList.setLayout(new BoxLayout(questionsList, BoxLayout.Y_AXIS));
        questionsList.setOpaque(false);
        addQuestionItem("When will the brickwork stage finish?",
                "We expect to finish it in about two weeks, weather permitting.");
        addQuestionItem("Can you upload more photos of the first floor?", null);
        page.add(createCard("My Questions", questionsList));

        submit.addActionListener(e -> {
            String q = input.getText().trim();
            if (q.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please type your question first.",
                        "Empty Question", JOptionPane.WARNING_MESSAGE);
                return;
            }
            addQuestionItem(q, null);
            input.setText("");
            JOptionPane.showMessageDialog(this, "Your question was sent to the supervisor.");
        });
        return wrapTop(page);
    }

    // adds one question to the list (answer == null means Pending)
    private void addQuestionItem(String question, String answer) {
        JPanel item = new JPanel(new BorderLayout(0, 6));
        item.setBackground(new Color(0xF8, 0xFA, 0xFD));
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE3, 0xE8, 0xF0)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel q = new JLabel("<html><body style='width:520px'><b>Q: </b>" + question + "</body></html>");
        q.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        q.setForeground(TEXT);

        JLabel status = (answer == null)
                ? createStatusLabel("Pending", ORANGE)
                : createStatusLabel("Answered", GREEN);

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        top.add(q, BorderLayout.CENTER);
        JPanel statusWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        statusWrap.setOpaque(false);
        statusWrap.add(status);
        top.add(statusWrap, BorderLayout.EAST);
        item.add(top, BorderLayout.NORTH);

        if (answer != null) {
            JLabel a = new JLabel("<html><body style='width:520px'><b>A: </b>" + answer + "</body></html>");
            a.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            a.setForeground(GRAY);
            item.add(a, BorderLayout.CENTER);
        } else {
            pendingCount++;
            if (pendingValueLabel != null)
                pendingValueLabel.setText(String.valueOf(pendingCount));
        }

        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, item.getPreferredSize().height + 10));
        questionsList.add(item, 0); // newest on top
        questionsList.add(Box.createVerticalStrut(8), 1);
        questionsList.revalidate();
        questionsList.repaint();
    }

    // =====================================================
    // PLACEHOLDER PAGE
    // =====================================================
    private JPanel createPlaceholderPage(String name) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(BG);
        JLabel l = new JLabel(name + " page will be added later.");
        l.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        l.setForeground(GRAY);
        p.add(l);
        return p;
    }

    // =====================================================
    // SMALL HELPER METHODS
    // =====================================================
    private JPanel createPageBox() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(BG);
        box.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        return box;
    }

    // keeps the page at its natural height (stops stretching)
    private JPanel wrapTop(JPanel page) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.add(page, BorderLayout.NORTH);
        return outer;
    }

    private JScrollPane wrapScroll(JPanel p) {
        JScrollPane sp = new JScrollPane(p);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    private JPanel createHeading(String title, String subtitle) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(TEXT);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        s.setForeground(GRAY);
        p.add(t);
        p.add(Box.createVerticalStrut(4));
        p.add(s);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        return p;
    }

    // white card with a title and any body component
    private JPanel createCard(String title, JComponent body) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE3, 0xE8, 0xF0)),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(TEXT);
        card.add(t, BorderLayout.NORTH);
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel createStatCard(String title, String value, Color accent) {
        JLabel v = new JLabel(value);
        return createStatCardWithLabel(title, v, accent);
    }

    private JPanel createStatCardWithLabel(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 0, 0, 0, accent),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setForeground(GRAY);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(TEXT);
        card.add(t, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    // one row: name | progress bar | status label
    private JPanel createProgressRow(String name, int percent, String status) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        JLabel n = new JLabel(name);
        n.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        n.setForeground(TEXT);
        n.setPreferredSize(new Dimension(190, 24));

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(percent);
        bar.setStringPainted(true);
        bar.setString(percent + "%");
        bar.setForeground(statusColor(status));

        JPanel statusWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        statusWrap.setOpaque(false);
        statusWrap.setPreferredSize(new Dimension(110, 24));
        statusWrap.add(createStatusLabel(status, statusColor(status)));

        row.add(n, BorderLayout.WEST);
        row.add(bar, BorderLayout.CENTER);
        row.add(statusWrap, BorderLayout.EAST);
        return row;
    }

    // status always has TEXT, not only color
    private JLabel createStatusLabel(String text, Color color) {
        JLabel l = new JLabel(" " + text + " ");
        l.setOpaque(true);
        l.setBackground(color);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private Color statusColor(String status) {
        switch (status) {
            case "Completed":
            case "On Track":
                return GREEN;
            case "In Progress":
                return BLUE;
            case "Delayed":
            case "Needs Attention":
                return ORANGE;
            default:
                return GRAY; // Not Started
        }
    }

    private JPanel createUpdateItem(String text, String time) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        JLabel a = new JLabel(text);
        a.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        a.setForeground(TEXT);
        JLabel t = new JLabel(time);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.setForeground(GRAY);
        p.add(a, BorderLayout.WEST);
        p.add(t, BorderLayout.EAST);
        return p;
    }

    private JButton createActionButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BLUE);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        return b;
    }

    private long totalSpent() {
        long sum = 0;
        for (Object[] row : EXPENSES)
            sum += (Long) row[2];
        return sum;
    }

    private String money(long amount) {
        return String.format("Rs. %,d", amount);
    }

    // for testing this screen alone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeownerDashboard("Test Homeowner").setVisible(true));
    }
}
