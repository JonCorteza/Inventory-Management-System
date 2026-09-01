package inventorymanagementsystem.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Top-level application window. Lays out the same shell across every
 * screen: title bar with the user avatar, a left navigation sidebar,
 * and a CardLayout content area that swaps in
 * {@link DashboardPanel}, {@link ProductManagementPanel}, etc.
 *
 * Note: the "Product Management" mockup didn't show a sidebar and the
 * "Reporting Dashboard" mockup did - this build keeps the sidebar on
 * every screen for a consistent, standard app shell. Remove it from
 * buildContent()/add() if you'd rather match the first mockup exactly.
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private final JPanel sidebar = new JPanel();

    public MainFrame() {
        super("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 780);
        setMinimumSize(new Dimension(1000, 640));
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        setLayout(new BorderLayout());
        add(buildTopBar(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);

        setActiveNav("Dashboard");
    }

    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UITheme.BG_APP);
        top.setBorder(new EmptyBorder(18, 24, 12, 24));

        JLabel title = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setFont(UITheme.FONT_TITLE);
        top.add(title, BorderLayout.WEST);

        JLabel avatar = new JLabel("\u25CF"); // simple circle placeholder for a user icon
        avatar.setForeground(UITheme.TEXT_MUTED);
        avatar.setFont(new Font("SansSerif", Font.PLAIN, 26));
        top.add(avatar, BorderLayout.EAST);

        return top;
    }

    private JComponent buildSidebar() {
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_SIDEBAR);
        sidebar.setBorder(new EmptyBorder(12, 0, 0, 0));
        sidebar.setPreferredSize(new Dimension(210, 0));

        addNavButton("Dashboard", "\u25A6");
        addNavButton("Product Management", "\u2630");
        addNavButton("Report Generation", "\u25B4");
        addNavButton("System Settings", "\u2699");

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private void addNavButton(String key, String icon) {
        JButton b = new JButton("  " + icon + "   " + key);
        b.setHorizontalAlignment(JButton.LEFT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setForeground(UITheme.TEXT_MUTED);
        b.setFont(UITheme.FONT_BODY);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> setActiveNav(key));
        navButtons.put(key, b);
        sidebar.add(b);
    }

    private void setActiveNav(String key) {
        for (Map.Entry<String, JButton> e : navButtons.entrySet()) {
            boolean active = e.getKey().equals(key);
            JButton b = e.getValue();
            b.setForeground(active ? UITheme.ACCENT_GREEN : UITheme.TEXT_MUTED);
            b.setBorder(active
                    ? new MatteBorder(0, 3, 0, 0, UITheme.ACCENT_GREEN)
                    : new EmptyBorder(0, 3, 0, 0));
            b.setOpaque(active);
            b.setContentAreaFilled(active);
            b.setBackground(UITheme.BG_PANEL);
        }
        cardLayout.show(contentPanel, key);
    }

    private JComponent buildContent() {
        contentPanel.setBackground(UITheme.BG_APP);
        contentPanel.setBorder(new EmptyBorder(0, 12, 12, 24));

        // "Dashboard" and "Report Generation" both point at the same
        // reporting view shown in the second mockup.
        contentPanel.add(new DashboardPanel(), "Dashboard");
        contentPanel.add(new ProductManagementPanel(), "Product Management");
        contentPanel.add(new DashboardPanel(), "Report Generation");
        contentPanel.add(new SettingsPanel(), "System Settings");

        return contentPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
