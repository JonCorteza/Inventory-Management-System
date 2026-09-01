package inventorymanagementsystem.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

/**
 * Central place for the dark / neon-green look shown in the mockups.
 * Change the constants here and the whole app re-skins.
 */
public final class UITheme {

    public static final Color BG_APP = new Color(0x0B, 0x0B, 0x0B);
    public static final Color BG_PANEL = new Color(0x14, 0x14, 0x14);
    public static final Color BG_SIDEBAR = new Color(0x10, 0x10, 0x10);
    public static final Color BG_INPUT = new Color(0x1A, 0x1A, 0x1A);
    public static final Color BG_TABLE_ALT = new Color(0x1D, 0x1D, 0x1D);

    public static final Color ACCENT_GREEN = new Color(0x39, 0xD3, 0x53);
    public static final Color ACCENT_GREEN_DIM = new Color(0x1F, 0x6B, 0x2C);
    public static final Color WARNING_YELLOW = new Color(0xE0, 0xB3, 0x3C);
    public static final Color DANGER_RED = new Color(0xE0, 0x4C, 0x4C);

    public static final Color TEXT_PRIMARY = new Color(0xF2, 0xF2, 0xF2);
    public static final Color TEXT_MUTED = new Color(0x8A, 0x8A, 0x8A);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 13);

    private UITheme() {
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(BG_APP);
    }

    /** Solid green "primary" action button (Add Product / Generate Report / etc.). */
    public static void stylePrimaryButton(JButton b) {
        b.setBackground(ACCENT_GREEN);
        b.setForeground(Color.BLACK);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** Outlined "secondary" button on a dark background (Update / Import…). */
    public static void styleSecondaryButton(JButton b) {
        b.setBackground(BG_PANEL);
        b.setForeground(ACCENT_GREEN);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(ACCENT_GREEN_DIM, 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleDangerButton(JButton b) {
        b.setBackground(BG_PANEL);
        b.setForeground(DANGER_RED);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(new Color(0x5A, 0x22, 0x22), 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleTextField(JTextField field) {
        field.setBackground(BG_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(ACCENT_GREEN);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0x2A, 0x2A, 0x2A), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_GREEN_DIM, 1, true),
                new EmptyBorder(14, 16, 14, 16));
    }

    public static JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_PRIMARY);
        l.setFont(FONT_HEADING);
        return l;
    }
}
