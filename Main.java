package inventorymanagementsystem;

import inventorymanagementsystem.db.DBConnection;
import inventorymanagementsystem.ui.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point. In NetBeans: right-click this file -> Run File
 * (or set it as the project's Main Class under Project Properties > Run).
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to the default look and feel.
        }

        SwingUtilities.invokeLater(() -> {
            if (!DBConnection.testConnection()) {
                JOptionPane.showMessageDialog(null,
                        "Could not connect to the 'inventory_management' database.\n"
                        + "Make sure XAMPP's MySQL service is running and the database\n"
                        + "has been imported (see sql/inventory_management.sql).",
                        "Database Connection", JOptionPane.WARNING_MESSAGE);
            }
            new MainFrame().setVisible(true);
        });
    }
}
