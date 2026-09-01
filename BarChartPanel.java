package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.ui.UITheme;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedHashMap;
import java.util.Map;

/** Minimal vertical bar chart, hand painted - feeds "Top Selling Categories". */
public class BarChartPanel extends JPanel {

    private Map<String, Integer> data = new LinkedHashMap<>();

    public BarChartPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(260, 120));
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(UITheme.TEXT_MUTED);
            g2.setFont(UITheme.FONT_BODY);
            g2.drawString("No sales data yet", 8, getHeight() / 2);
            g2.dispose();
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int max = 1;
        for (int v : data.values()) {
            max = Math.max(max, v);
        }

        int padding = 10;
        int labelH = 18;
        int chartH = getHeight() - padding * 2 - labelH;
        int barCount = data.size();
        int gap = 14;
        int barW = Math.max(18, (getWidth() - padding * 2 - gap * (barCount - 1)) / Math.max(barCount, 1));

        int xCur = padding;
        for (Map.Entry<String, Integer> e : data.entrySet()) {
            int barH = (int) (chartH * (e.getValue() / (double) max));
            int yTop = padding + (chartH - barH);

            g2.setColor(UITheme.ACCENT_GREEN);
            g2.fillRoundRect(xCur, yTop, barW, barH, 6, 6);

            g2.setColor(UITheme.TEXT_MUTED);
            g2.setFont(UITheme.FONT_BODY.deriveFont(10f));
            String label = e.getKey() == null ? "?" : e.getKey();
            if (label.length() > 8) {
                label = label.substring(0, 7) + "\u2026";
            }
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, xCur + (barW - fm.stringWidth(label)) / 2, padding + chartH + 14);

            xCur += barW + gap;
        }

        g2.dispose();
    }
}
