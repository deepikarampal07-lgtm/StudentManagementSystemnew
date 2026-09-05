import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Next-level UI for the existing StudentDAO/Student classes.

 */
public class StudentManagementGUI extends JFrame {
    private static final Color LIGHT_BG = new Color(244, 246, 251);
    private static final Color LIGHT_CARD = Color.WHITE;
    private static final Color LIGHT_TEXT = new Color(31, 41, 55);
    private static final Color LIGHT_MUTED = new Color(100, 116, 139);
    private static final Color DARK_BG = new Color(12, 18, 30);
    private static final Color DARK_CARD = new Color(21, 29, 45);
    private static final Color DARK_TEXT = new Color(241, 245, 249);
    private static final Color DARK_MUTED = new Color(156, 163, 175);
    private static final Color PRIMARY = new Color(99, 71, 255);
    private static final Color PRIMARY_2 = new Color(47, 128, 237);
    private static final Color SUCCESS = new Color(18, 160, 103);
    private static final Color DANGER = new Color(220, 72, 72);
    private static final Color WARNING = new Color(232, 146, 24);

    private boolean darkMode = true;
    private final StudentDAO dao = new StudentDAO();

    private CardLayout mainCards;
    private JPanel mainPanel;
    private JPanel dashboardView;
    private JPanel studentsView;
    private JLabel totalStudentsLabel;
    private JLabel statusLabel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentManagementGUI() {
        showLogin();
    }

    // ---------------- Login ----------------
    private void showLogin() {
        setTitle("Student Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(820, 560));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(DARK_BG);
        setContentPane(root);

        JPanel left = new GradientPanel(PRIMARY_2, PRIMARY);
        left.setPreferredSize(new Dimension(390, 0));
        left.setLayout(new GridBagLayout());
        JPanel branding = new JPanel();
        branding.setOpaque(false);
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));

        JLabel cap = centerLabel("🎓");
        cap.setFont(new Font("SansSerif", Font.PLAIN, 72));
        JLabel title = centerLabel("Student\nManagement");
        title.setText("Student Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel title2 = centerLabel("SYSTEM");
        title2.setForeground(new Color(232, 228, 255));
        title2.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel desc = centerLabel("Manage student records\nwith ease and confidence.");
        desc.setText("Manage student records with ease\nand confidence.");
        desc.setForeground(new Color(241, 245, 249));
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));

        branding.add(cap);
        branding.add(Box.createVerticalStrut(10));
        branding.add(title);
        branding.add(title2);
        branding.add(Box.createVerticalStrut(16));
        branding.add(desc);
        left.add(branding);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(DARK_BG);
        JPanel card = new JPanel();
        card.setBackground(DARK_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 58, 80)),
                new EmptyBorder(34, 38, 34, 38)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome back 👋");
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcome.setForeground(DARK_TEXT);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel sub = new JLabel("Sign in to open your dashboard");
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setForeground(DARK_MUTED);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JTextField user = field("Username", "admin");
        JPasswordField pass = passwordField("Password", "admin123");

        JLabel hint = new JLabel("Demo login:  admin  /  admin123");
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        hint.setForeground(new Color(167, 139, 250));
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JButton login = button("LOGIN", PRIMARY, Color.WHITE);
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        login.addActionListener(e -> {
            String u = user.getText().trim();
            String p = new String(pass.getPassword());
            if (u.equals("admin") && p.equals("admin123")) {
                buildApplication();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        getRootPane().setDefaultButton(login);

        card.add(welcome);
        card.add(Box.createVerticalStrut(5));
        card.add(sub);
        card.add(Box.createVerticalStrut(25));
        card.add(labelFor("USERNAME"));
        card.add(Box.createVerticalStrut(6));
        card.add(user);
        card.add(Box.createVerticalStrut(16));
        card.add(labelFor("PASSWORD"));
        card.add(Box.createVerticalStrut(6));
        card.add(pass);
        card.add(Box.createVerticalStrut(10));
        card.add(hint);
        card.add(Box.createVerticalStrut(22));
        card.add(login);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(25, 30, 25, 30);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.weighty = 1;
        right.add(card, gc);

        root.add(left, BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        setVisible(true);
    }

    private JLabel labelFor(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(DARK_MUTED);
        l.setFont(new Font("SansSerif", Font.BOLD, 10));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField field(String placeholder, String initial) {
        JTextField f = new JTextField(initial);
        styleInput(f, DARK_CARD, DARK_TEXT);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return f;
    }

    private JPasswordField passwordField(String placeholder, String initial) {
        JPasswordField f = new JPasswordField(initial);
        styleInput(f, DARK_CARD, DARK_TEXT);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return f;
    }

    private void styleInput(JTextField f, Color bg, Color fg) {
        f.setBackground(bg);
        f.setForeground(fg);
        f.setCaretColor(fg);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 68, 92)),
                new EmptyBorder(10, 12, 10, 12)
        ));
    }

    // ---------------- Application ----------------
    private void buildApplication() {
        setTitle("Student Management System - Dashboard");
        setSize(1200, 760);
        setMinimumSize(new Dimension(980, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(currentBg());
        setContentPane(root);

        root.add(buildSidebar(), BorderLayout.WEST);

        mainCards = new CardLayout();
        mainPanel = new JPanel(mainCards);
        mainPanel.setOpaque(false);

        dashboardView = buildDashboardView();
        studentsView = buildStudentsView();
        JPanel settings = buildSettingsView();

        mainPanel.add(dashboardView, "dashboard");
        mainPanel.add(studentsView, "students");
        mainPanel.add(settings, "settings");
        root.add(mainPanel, BorderLayout.CENTER);

        mainCards.show(mainPanel, "dashboard");
        setVisible(true);
        refreshData();
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(235, 0));
        side.setBackground(darkMode ? new Color(9, 14, 24) : new Color(28, 34, 48));
        side.setBorder(new EmptyBorder(22, 16, 18, 16));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        logoRow.setOpaque(false);
        JLabel logo = new JLabel("◉");
        logo.setFont(new Font("SansSerif", Font.BOLD, 32));
        logo.setForeground(new Color(137, 112, 255));
        JLabel brand = new JLabel("SMS");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 20));
        logoRow.add(logo);
        logoRow.add(brand);
        top.add(logoRow);
        top.add(Box.createVerticalStrut(35));

        top.add(navButton("⌂   Dashboard", "dashboard", true));
        top.add(Box.createVerticalStrut(8));
        top.add(navButton("♙   Students", "students", false));
        top.add(Box.createVerticalStrut(8));

        JButton add = navButton("＋   Add Student", "add", false);
        top.add(add);
        top.add(Box.createVerticalStrut(8));

        JButton settings = navButton("⚙   Settings", "settings", false);
        top.add(settings);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JButton mode = navButton(darkMode ? "☀   Light Mode" : "☾   Dark Mode", "mode", false);
        mode.addActionListener(e -> {
            darkMode = !darkMode;
            buildApplication();
        });
        bottom.add(mode);
        bottom.add(Box.createVerticalStrut(8));

        JButton logout = navButton("⇥   Logout", "logout", false);
        logout.setForeground(new Color(248, 113, 113));
        logout.addActionListener(e -> showLogin());
        bottom.add(logout);

        side.add(top, BorderLayout.NORTH);
        side.add(bottom, BorderLayout.SOUTH);
        return side;
    }

    private JButton navButton(String text, String action, boolean active) {
        JButton b = new JButton(text);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(active ? Color.WHITE : new Color(198, 206, 220));
        b.setBackground(active ? PRIMARY : new Color(18, 25, 39));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (action.equals("dashboard")) b.addActionListener(e -> mainCards.show(mainPanel, "dashboard"));
        if (action.equals("students")) b.addActionListener(e -> mainCards.show(mainPanel, "students"));
        if (action.equals("add")) b.addActionListener(e -> showAddStudentDialog());
        if (action.equals("settings")) b.addActionListener(e -> mainCards.show(mainPanel, "settings"));
        return b;
    }

    private JPanel contentPage(String heading, String subtitle) {
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setOpaque(false);
        page.setBorder(new EmptyBorder(25, 28, 25, 28));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel h = new JLabel(heading);
        h.setForeground(currentText());
        h.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel s = new JLabel(subtitle);
        s.setForeground(currentMuted());
        s.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        labels.add(h);
        labels.add(Box.createVerticalStrut(4));
        labels.add(s);
        header.add(labels, BorderLayout.WEST);
        page.add(header, BorderLayout.NORTH);
        return page;
    }

    private JPanel buildDashboardView() {
        JPanel page = contentPage("Dashboard", "Welcome back, Admin. Here's your student overview.");
        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);
        totalStudentsLabel = new JLabel("0");
        stats.add(statCard("Total Students", totalStudentsLabel, "Registered records", PRIMARY));
        stats.add(statCard("Database", new JLabel("ONLINE"), "MySQL connection", SUCCESS));
        stats.add(statCard("App", new JLabel("READY"), "Java Swing interface", WARNING));

        JPanel welcomeCard = cardPanel();
        welcomeCard.setLayout(new BorderLayout(18, 10));
        JLabel big = new JLabel("Manage everything from one place");
        big.setForeground(currentText());
        big.setFont(new Font("SansSerif", Font.BOLD, 21));
        JLabel desc = new JLabel("Add new students, update existing records, search quickly, or remove records with confirmation.");
        desc.setForeground(currentMuted());
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(big);
        text.add(Box.createVerticalStrut(8));
        text.add(desc);
        welcomeCard.add(text, BorderLayout.CENTER);

        JButton add = button("＋  ADD STUDENT", PRIMARY, Color.WHITE);
        add.addActionListener(e -> showAddStudentDialog());
        welcomeCard.add(add, BorderLayout.EAST);

        body.add(stats, BorderLayout.NORTH);
        body.add(welcomeCard, BorderLayout.CENTER);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel statCard(String name, JLabel value, String sub, Color accent) {
        JPanel card = cardPanel();
        card.setLayout(new BorderLayout(12, 4));
        JPanel icon = new JPanel(new GridBagLayout());
        icon.setPreferredSize(new Dimension(50, 50));
        icon.setBackground(accent);
        JLabel dot = new JLabel("●");
        dot.setForeground(Color.WHITE);
        dot.setFont(new Font("SansSerif", Font.BOLD, 16));
        icon.add(dot);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel n = new JLabel(name);
        n.setForeground(currentMuted());
        n.setFont(new Font("SansSerif", Font.BOLD, 12));
        value.setForeground(currentText());
        value.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel ss = new JLabel(sub);
        ss.setForeground(currentMuted());
        ss.setFont(new Font("SansSerif", Font.PLAIN, 11));
        text.add(n); text.add(value); text.add(ss);
        card.add(icon, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildStudentsView() {
        JPanel page = contentPage("All Students", "Search, select, update and manage student records.");
        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel tools = new JPanel(new BorderLayout(10, 0));
        tools.setOpaque(false);
        searchField = new JTextField();
        searchField.setToolTipText("Search by name, course, email or phone");
        styleInput(searchField, currentCard(), currentText());
        searchField.setPreferredSize(new Dimension(300, 38));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton add = button("＋ Add Student", PRIMARY, Color.WHITE);
        add.addActionListener(e -> showAddStudentDialog());
        JButton edit = button("✎ Edit", PRIMARY_2, Color.WHITE);
        edit.addActionListener(e -> editSelected());
        JButton del = button("▣ Delete", DANGER, Color.WHITE);
        del.addActionListener(e -> deleteSelected());
        JButton refresh = button("↻ Refresh", new Color(91, 104, 124), Color.WHITE);
        refresh.addActionListener(e -> refreshData());
        actions.add(add); actions.add(edit); actions.add(del); actions.add(refresh);
        tools.add(searchField, BorderLayout.WEST);
        tools.add(actions, BorderLayout.EAST);

        JPanel tableCard = cardPanel();
        tableCard.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "NAME", "COURSE", "EMAIL", "PHONE"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(38);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(currentText());
        table.setBackground(currentCard());
        table.setSelectionBackground(new Color(76, 63, 180));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(darkMode ? new Color(43, 55, 76) : new Color(226, 232, 240));
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        table.getTableHeader().setForeground(currentMuted());
        table.getTableHeader().setBackground(darkMode ? new Color(17, 24, 39) : new Color(248, 250, 252));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2) editSelected(); }
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(currentCard());
        tableCard.add(scroll, BorderLayout.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(currentMuted());
        statusLabel.setBorder(new EmptyBorder(8, 2, 0, 0));
        body.add(tools, BorderLayout.NORTH);
        body.add(tableCard, BorderLayout.CENTER);
        body.add(statusLabel, BorderLayout.SOUTH);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel buildSettingsView() {
        JPanel page = contentPage("Settings", "Simple interface preferences for the desktop app.");
        JPanel card = cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Appearance");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(currentText());
        JLabel info = new JLabel("Switch between dark and light mode from the sidebar.");
        info.setForeground(currentMuted());
        info.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton toggle = button(darkMode ? "☀  Switch to Light Mode" : "☾  Switch to Dark Mode", PRIMARY, Color.WHITE);
        toggle.setAlignmentX(Component.LEFT_ALIGNMENT);
        toggle.addActionListener(e -> { darkMode = !darkMode; buildApplication(); });
        card.add(title); card.add(Box.createVerticalStrut(8)); card.add(info); card.add(Box.createVerticalStrut(18)); card.add(toggle);
        page.add(card, BorderLayout.CENTER);
        return page;
    }

    // ---------------- Data ----------------
    private void refreshData() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        List<Student> students = dao.getAllStudents();
        for (Student s : students) addRow(s);
        updateStats(students.size());
        if (statusLabel != null) statusLabel.setText(students.size() + " student(s) loaded");
    }

    private void addRow(Student s) {
        tableModel.addRow(new Object[]{safe(s.getId()), safe(s.getName()), safe(s.getCourse()), safe(s.getEmail()), safe(s.getPhone())});
    }

    private void filterTable() {
        if (tableModel == null || searchField == null) return;
        String q = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        int count = 0;
        for (Student s : dao.getAllStudents()) {
            String all = (safe(s.getName()) + " " + safe(s.getCourse()) + " " + safe(s.getEmail()) + " " + safe(s.getPhone())).toLowerCase();
            if (q.isEmpty() || all.contains(q)) { addRow(s); count++; }
        }
        statusLabel.setText(count + " result(s)");
    }

    private void updateStats(int count) {
        if (totalStudentsLabel != null) totalStudentsLabel.setText(String.valueOf(count));
    }

    // ---------------- Dialogs ----------------
    private void showAddStudentDialog() {
        JTextField name = new JTextField();
        JTextField course = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JPanel form = formPanel("Add New Student", name, course, email, phone);
        int result = JOptionPane.showConfirmDialog(this, form, "Add Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;
        if (!validateInput(name.getText(), course.getText(), email.getText(), phone.getText())) return;
        Student s = new Student(0, name.getText().trim(), course.getText().trim(), email.getText().trim(), phone.getText().trim());
        if (dao.addStudent(s)) { showInfo("Student added successfully!"); refreshData(); }
        else showError("Could not add student. Check MySQL connection.");
    }

    private void editSelected() {
        int row = table == null ? -1 : table.getSelectedRow();
        if (row < 0) { showError("Please select a student first."); return; }
        int id = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 0)));
        JTextField name = new JTextField(String.valueOf(tableModel.getValueAt(row, 1)));
        JTextField course = new JTextField(String.valueOf(tableModel.getValueAt(row, 2)));
        JTextField email = new JTextField(String.valueOf(tableModel.getValueAt(row, 3)));
        JTextField phone = new JTextField(String.valueOf(tableModel.getValueAt(row, 4)));
        JPanel form = formPanel("Edit Student", name, course, email, phone);
        int result = JOptionPane.showConfirmDialog(this, form, "Edit Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;
        if (!validateInput(name.getText(), course.getText(), email.getText(), phone.getText())) return;
        Student s = new Student(id, name.getText().trim(), course.getText().trim(), email.getText().trim(), phone.getText().trim());
        if (dao.updateStudent(s)) { showInfo("Student updated successfully!"); refreshData(); }
        else showError("Could not update student.");
    }

    private void deleteSelected() {
        int row = table == null ? -1 : table.getSelectedRow();
        if (row < 0) { showError("Please select a student first."); return; }
        int id = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 0)));
        String name = String.valueOf(tableModel.getValueAt(row, 1));
        int choice = JOptionPane.showConfirmDialog(this, "Delete '" + name + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) return;
        if (dao.deleteStudent(id)) { showInfo("Student deleted successfully!"); refreshData(); }
        else showError("Could not delete student.");
    }

    private JPanel formPanel(String title, JTextField name, JTextField course, JTextField email, JTextField phone) {
        JPanel outer = new JPanel(new BorderLayout(0, 14));
        outer.setBackground(currentCard());
        outer.setBorder(new EmptyBorder(12, 14, 10, 14));

        JLabel h = new JLabel(title);
        h.setFont(new Font("SansSerif", Font.BOLD, 19));
        h.setForeground(currentText());
        outer.add(h, BorderLayout.NORTH);

        JPanel p = new JPanel(new GridLayout(4, 2, 12, 12));
        p.setBackground(currentCard());
        p.add(labelFor2("Full Name")); p.add(styleDialogField(name));
        p.add(labelFor2("Course")); p.add(styleDialogField(course));
        p.add(labelFor2("Email")); p.add(styleDialogField(email));
        p.add(labelFor2("Phone")); p.add(styleDialogField(phone));
        outer.add(p, BorderLayout.CENTER);
        return outer;
    }

    private JTextField styleDialogField(JTextField field) {
        field.setBackground(darkMode ? new Color(27, 37, 56) : Color.WHITE);
        field.setForeground(currentText());
        field.setCaretColor(currentText());
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkMode ? new Color(65, 82, 109) : new Color(203, 213, 225)),
                new EmptyBorder(7, 9, 7, 9)
        ));
        return field;
    }

    private JLabel labelFor2(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(currentText());
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        return l;
    }

    private boolean validateInput(String name, String course, String email, String phone) {
        if (name.trim().isEmpty() || course.trim().isEmpty()) { showError("Name and Course are required."); return false; }
        if (!email.trim().isEmpty() && !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) { showError("Enter a valid email."); return false; }
        if (!phone.trim().isEmpty() && !phone.matches("\\d{10,15}")) { showError("Phone must contain 10-15 digits."); return false; }
        return true;
    }

    private void showInfo(String msg) { JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }

    // ---------------- Styling helpers ----------------
    private JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(currentCard());
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkMode ? new Color(40, 54, 75) : new Color(226, 232, 240)),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return p;
    }

    private JButton button(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(130, 38));
        return b;
    }

    private JLabel centerLabel(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private String safe(Object o) { return o == null || "null".equals(o) ? "" : String.valueOf(o); }
    private Color currentBg() { return darkMode ? DARK_BG : LIGHT_BG; }
    private Color currentCard() { return darkMode ? DARK_CARD : LIGHT_CARD; }
    private Color currentText() { return darkMode ? DARK_TEXT : LIGHT_TEXT; }
    private Color currentMuted() { return darkMode ? DARK_MUTED : LIGHT_MUTED; }

    private static class GradientPanel extends JPanel {
        private final Color a, b;
        GradientPanel(Color a, Color b) { this.a = a; this.b = b; }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth(), h = getHeight();
            if (w > 0 && h > 0) {
                g2.setPaint(new GradientPaint(0, 0, a, w, h, b));
                g2.fillRect(0, 0, w, h);
            }
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new StudentManagementGUI();
        });
    }
}
