package inventorymanagementsystem.ui;

import inventorymanagementsystem.db.DBConnection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/** Placeholder "System Settings" screen - extend with real preferences as needed. */
public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(6, 0, 0, 0));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UITheme.BG_PANEL);
        card.setBorder(UITheme.cardBorder());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(6, 0, 6, 0);

        JLabel title = UITheme.sectionTitle("Database Connection");
        card.add(title, gc);

        gc.gridy++;
        boolean ok = DBConnection.testConnection();
        JLabel status = new JLabel(ok
                ? "\u25CF Connected to inventory_management via XAMPP"
                : "\u25CF Not connected");
        status.setForeground(ok ? UITheme.ACCENT_GREEN : UITheme.DANGER_RED);
        status.setFont(UITheme.FONT_BODY);
        card.add(status, gc);

        gc.gridy++;
        JButton retest = new JButton("Test Connection");
        UITheme.styleSecondaryButton(retest);
        retest.addActionListener(e -> {
            DBConnection.closeConnection();
            boolean nowOk = DBConnection.testConnection();
            status.setText(nowOk
                    ? "\u25CF Connected to inventory_management via XAMPP"
                    : "\u25CF Not connected");
            status.setForeground(nowOk ? UITheme.ACCENT_GREEN : UITheme.DANGER_RED);
        });
        card.add(retest, gc);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setOpaque(false);
        wrapper.add(card);
        add(wrapper, BorderLayout.NORTH);
    }
}
