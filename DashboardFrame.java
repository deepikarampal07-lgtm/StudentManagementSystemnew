import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JLabel totalLabel;
    private JLabel statusLabel;
    private JTextField searchField;
    private JPanel content;
    private JPanel header;
    private JLabel pageTitle;
    private boolean dark = Theme.dark;
    private final StudentDAO dao = new StudentDAO();

    public DashboardFrame() {
        setTitle("Student Management System - Dashboard");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
        loadStudents();
    }

    private void buildUi() {
        Container cp = getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Theme.bg());

        cp.add(buildSidebar(), BorderLayout.WEST);

        content = new JPanel(new BorderLayout(0, 14));
        content.setBorder(new EmptyBorder(18, 18, 18, 18));
        content.setBackground(Theme.bg());
        header = buildHeader();
        content.add(header, BorderLayout.NORTH);
        content.add(buildCenter(), BorderLayout.CENTER);
        cp.add(content, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(220, 0));
        side.setBackground(Theme.surface());
        side.setBorder(new EmptyBorder(20, 14, 20, 14));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🎓  SMS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Theme.secondary());
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logoSub = new JLabel("  Student Management System");
        logoSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        logoSub.setForeground(Theme.muted());
        logoSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        side.add(logo);
        side.add(Box.createVerticalStrut(2));
        side.add(logoSub);
        side.add(Box.createVerticalStrut(28));

        side.add(nav("▦", "Dashboard", true, e -> refreshDashboard()));
        side.add(Box.createVerticalStrut(8));
        side.add(nav("◉", "Students", false, e -> table.requestFocusInWindow()));
        side.add(Box.createVerticalStrut(8));
        side.add(nav("＋", "Add Student", false, e -> openStudentDialog(null)));
        side.add(Box.createVerticalGlue());

        JButton theme = nav("◐", "Toggle Theme", false, e -> toggleTheme());
        side.add(theme);
        side.add(Box.createVerticalStrut(8));
        side.add(nav("⇦", "Logout", false, e -> {
            dispose();
            new LoginFrame().setVisible(true);
        }));

        return side;
    }

    private JButton nav(String icon, String text, boolean selected, java.awt.event.ActionListener action) {
        JButton b = new JButton(icon + "   " + text);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(selected ? Color.WHITE : Theme.muted());
        b.setBackground(selected ? Theme.primary() : Theme.surface());
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setMaximumSize(new Dimension(192, 42));
        b.setPreferredSize(new Dimension(192, 42));
        b.addActionListener(action);
        return b;
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 27));
        pageTitle.setForeground(Theme.text());

        JLabel sub = new JLabel("Welcome back, Admin 👋");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(Theme.muted());

        left.add(pageTitle);
        left.add(Box.createVerticalStrut(3));
        left.add(sub);

        JButton add = actionButton("+ Add Student", Theme.success());
        add.addActionListener(e -> openStudentDialog(null));
        p.add(left, BorderLayout.WEST);
        p.add(add, BorderLayout.EAST);
        return p;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(buildStats(), BorderLayout.NORTH);
        center.add(buildTableCard(), BorderLayout.CENTER);
        return center;
    }

    private JPanel buildStats() {
        JPanel p = new JPanel(new GridLayout(1, 3, 12, 0));
        p.setOpaque(false);
        p.add(statCard("👥", "Total Students", "0", Theme.primary()));
        p.add(statCard("📚", "Active Courses", "-", Theme.secondary()));
        p.add(statCard("✓", "System Status", "Online", Theme.success()));
        return p;
    }

    private JPanel statCard(String icon, String label, String value, Color accent) {
        JPanel c = new JPanel(new BorderLayout(12, 0));
        c.setBackground(Theme.surface());
        c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(14, 16, 14, 16)));

        JLabel i = new JLabel(icon);
        i.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        i.setForeground(accent);

        JPanel txt = new JPanel();
        txt.setOpaque(false);
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(Theme.muted());
        totalLabel = value.equals("0") ? new JLabel(value) : totalLabel;
        JLabel v = value.equals("0") ? totalLabel : new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 25));
        v.setForeground(Theme.text());
        txt.add(l);
        txt.add(Box.createVerticalStrut(2));
        txt.add(v);
        c.add(i, BorderLayout.WEST);
        c.add(txt, BorderLayout.CENTER);
        return c;
    }

    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Theme.surface());
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(14, 14, 14, 14)));

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        JLabel title = new JLabel("Student Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(Theme.text());

        JPanel tools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tools.setOpaque(false);
        searchField = new JTextField();
        searchField.setToolTipText("Search name, course, email or phone");
        searchField.setPreferredSize(new Dimension(240, 36));
        styleField(searchField);
        JButton search = actionButton("Search", Theme.primary());
        search.addActionListener(e -> loadStudents(searchField.getText().trim()));
        JButton refresh = actionButton("Refresh", Theme.secondary());
        refresh.addActionListener(e -> { searchField.setText(""); loadStudents(); });
        tools.add(searchField);
        tools.add(search);
        tools.add(refresh);
        top.add(title, BorderLayout.WEST);
        top.add(tools, BorderLayout.EAST);

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Course", "Email", "Phone"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);
        table.setGridColor(Theme.border());
        styleTable();

        JPopupMenu menu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Edit selected");
        JMenuItem del = new JMenuItem("Delete selected");
        edit.addActionListener(e -> editSelected());
        del.addActionListener(e -> deleteSelected());
        menu.add(edit); menu.add(del);
        table.setComponentPopupMenu(menu);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.border()));
        scroll.getViewport().setBackground(Theme.surface());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(Theme.muted());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton manage = actionButton("Edit Selected", Theme.warning());
        manage.addActionListener(e -> editSelected());
        JButton del = actionButton("Delete Selected", Theme.danger());
        del.addActionListener(e -> deleteSelected());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(manage); right.add(del);
        bottom.add(statusLabel, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private void styleTable() {
        table.setBackground(Theme.surface());
        table.setForeground(Theme.text());
        table.setSelectionBackground(new Color(76, 88, 150));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(Theme.surface2());
        table.getTableHeader().setForeground(Theme.text());
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBackground(Theme.surface2());
        f.setForeground(Theme.text());
        f.setCaretColor(Theme.text());
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(6, 10, 6, 10)));
    }

    private JButton actionButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(112, 36));
        return b;
    }

    private void loadStudents() { loadStudents(""); }

    private void loadStudents(String query) {
        List<Student> students = dao.getAllStudents();
        model.setRowCount(0);
        int count = 0;
        String q = query == null ? "" : query.toLowerCase();
        for (Student s : students) {
            String searchable = (safe(s.getName()) + " " + safe(s.getCourse()) + " " + safe(s.getEmail()) + " " + safe(s.getPhone())).toLowerCase();
            if (q.isEmpty() || searchable.contains(q)) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getCourse(), s.getEmail(), s.getPhone()});
                count++;
            }
        }
        if (totalLabel != null) totalLabel.setText(String.valueOf(query == null || query.isEmpty() ? students.size() : count));
        if (statusLabel != null) statusLabel.setText(count + " student record(s) shown");
    }

    private void openStudentDialog(Student existing) {
        JDialog d = new JDialog(this, existing == null ? "Add New Student" : "Edit Student", true);
        d.setSize(480, 430);
        d.setLocationRelativeTo(this);
        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(Theme.bg());
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(Theme.surface());
        form.setBorder(new EmptyBorder(18, 18, 18, 18));
        JTextField name = new JTextField(existing == null ? "" : existing.getName());
        JTextField course = new JTextField(existing == null ? "" : existing.getCourse());
        JTextField email = new JTextField(existing == null ? "" : existing.getEmail());
        JTextField phone = new JTextField(existing == null ? "" : existing.getPhone());
        JTextField id = new JTextField(existing == null ? "Auto" : String.valueOf(existing.getId()));
        id.setEditable(false);
        styleField(id); styleField(name); styleField(course); styleField(email); styleField(phone);
        form.add(label("ID")); form.add(id);
        form.add(label("Name *")); form.add(name);
        form.add(label("Course *")); form.add(course);
        form.add(label("Email")); form.add(email);
        form.add(label("Phone")); form.add(phone);

        JButton save = actionButton(existing == null ? "Save Student" : "Update Student", Theme.primary());
        save.addActionListener(e -> {
            if (name.getText().trim().isEmpty() || course.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(d, "Name and Course are required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Student s = new Student(existing == null ? 0 : existing.getId(), name.getText().trim(), course.getText().trim(), email.getText().trim(), phone.getText().trim());
            boolean ok = existing == null ? dao.addStudent(s) : dao.updateStudent(s);
            if (ok) {
                d.dispose();
                loadStudents();
                JOptionPane.showMessageDialog(this, existing == null ? "Student added successfully!" : "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(d, "Database operation failed. Check MySQL/DBConnection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancel = actionButton("Cancel", new Color(110, 120, 140));
        cancel.addActionListener(e -> d.dispose());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false); actions.add(cancel); actions.add(save);
        root.add(form, BorderLayout.CENTER);
        root.add(actions, BorderLayout.SOUTH);
        d.setContentPane(root);
        d.setVisible(true);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.muted());
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a student first.", "Edit", JOptionPane.WARNING_MESSAGE); return; }
        Student s = new Student(
                Integer.parseInt(model.getValueAt(row, 0).toString()),
                model.getValueAt(row, 1).toString(),
                model.getValueAt(row, 2).toString(),
                safe(model.getValueAt(row, 3)),
                safe(model.getValueAt(row, 4)));
        openStudentDialog(s);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a student first.", "Delete", JOptionPane.WARNING_MESSAGE); return; }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int ok = JOptionPane.showConfirmDialog(this, "Delete selected student?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION && dao.deleteStudent(id)) loadStudents();
    }

    private void toggleTheme() {
        Theme.dark = !Theme.dark;
        SwingUtilities.updateComponentTreeUI(this);
        buildUi();
        loadStudents();
        revalidate(); repaint();
    }

    private void refreshDashboard() { searchField.setText(""); loadStudents(); }

    private String safe(Object o) { return o == null || "null".equals(o.toString()) ? "" : o.toString(); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
    }
}
