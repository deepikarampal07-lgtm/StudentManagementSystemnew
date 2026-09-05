import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Student Management System - Login");
        setSize(880, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.bg());
        setContentPane(root);

        JPanel brand = new JPanel();
        brand.setBackground(Theme.primary());
        brand.setBorder(new EmptyBorder(40, 40, 40, 40));
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Simple • Secure • Professional");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(235, 240, 255));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea info = new JTextArea("Manage your student records from one modern dashboard.\n\nAdd, update, search and delete students with your MySQL database.");
        info.setWrapStyleWord(true);
        info.setLineWrap(true);
        info.setEditable(false);
        info.setOpaque(false);
        info.setForeground(new Color(230, 235, 250));
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.setBorder(new EmptyBorder(24, 0, 0, 0));

        brand.add(icon);
        brand.add(Box.createVerticalStrut(10));
        brand.add(title);
        brand.add(Box.createVerticalStrut(6));
        brand.add(subtitle);
        brand.add(info);

        JPanel formWrap = new JPanel(new GridBagLayout());
        formWrap.setBackground(Theme.bg());
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.surface());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(30, 34, 30, 34)));
        card.setPreferredSize(new Dimension(360, 370));

        JLabel loginTitle = new JLabel("Welcome Back");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        loginTitle.setForeground(Theme.text());
        loginTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hint = new JLabel("Sign in to continue");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hint.setForeground(Theme.muted());
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(loginTitle);
        card.add(Box.createVerticalStrut(6));
        card.add(hint);
        card.add(Box.createVerticalStrut(26));

        usernameField = field("Username");
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(290, 42));
        passwordField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(6, 10, 6, 10)));
        passwordField.setBackground(Theme.surface2());
        passwordField.setForeground(Theme.text());
        passwordField.setCaretColor(Theme.text());

        card.add(label("Username"));
        card.add(Box.createVerticalStrut(6));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(16));
        card.add(label("Password"));
        card.add(Box.createVerticalStrut(6));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(18));

        JLabel creds = new JLabel("Demo login: admin / admin123");
        creds.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        creds.setForeground(Theme.secondary());
        creds.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(creds);
        card.add(Box.createVerticalStrut(18));

        JButton login = button("LOGIN", Theme.primary());
        login.addActionListener(e -> login());
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(login);

        JButton exit = new JButton("Exit");
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setForeground(Theme.muted());
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.addActionListener(e -> System.exit(0));
        card.add(Box.createVerticalStrut(10));
        card.add(exit);

        formWrap.add(card);
        root.add(brand, BorderLayout.WEST);
        root.add(formWrap, BorderLayout.CENTER);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.muted());
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField field(String hint) {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setPreferredSize(new Dimension(290, 42));
        f.setMaximumSize(new Dimension(290, 42));
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.border()), new EmptyBorder(6, 10, 6, 10)));
        f.setBackground(Theme.surface2());
        f.setForeground(Theme.text());
        f.setCaretColor(Theme.text());
        return f;
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(290, 42));
        b.setMaximumSize(new Dimension(290, 42));
        return b;
    }

    private void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());
        if (user.equals("admin") && pass.equals("admin123")) {
            dispose();
            new DashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.\nUse admin / admin123 for the demo.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
