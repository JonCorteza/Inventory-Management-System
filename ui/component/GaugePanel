package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.ui.UITheme;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/** Simple semicircular gauge, hand painted - no charting library needed. */
public class GaugePanel extends JPanel {

    private double percent; // 0..100

    public GaugePanel(double percent) {
        this.percent = percent;
        setOpaque(false);
        setPreferredSize(new Dimension(160, 100));
    }

    public void setPercent(double percent) {
        this.percent = percent;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int diameter = Math.min(w - 20, (h - 20) * 2);
        int x = (w - diameter) / 2;
        int y = h - diameter / 2 - 20;
        int stroke = 12;

        g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(0x2A, 0x2A, 0x2A));
        g2.drawArc(x, y, diameter, diameter, 0, 180);

        g2.setColor(UITheme.ACCENT_GREEN);
        int extent = (int) Math.round(180 * (percent / 100.0));
        g2.drawArc(x, y, diameter, diameter, 180, -extent);

        double angle = Math.toRadians(180 - (180 * (percent / 100.0)));
        int cx = x + diameter / 2;
        int cy = y + diameter / 2;
        int nx = cx + (int) ((diameter / 2.0 - stroke) * Math.cos(angle));
        int ny = cy - (int) ((diameter / 2.0 - stroke) * Math.sin(angle));
        g2.setStroke(new BasicStroke(2));
        g2.setColor(UITheme.TEXT_MUTED);
        g2.drawLine(cx, cy, nx, ny);

        g2.setColor(UITheme.TEXT_PRIMARY);
        g2.setFont(UITheme.FONT_TITLE);
        String label = Math.round(percent) + "%";
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(label, cx - fm.stringWidth(label) / 2, cy - 6);

        g2.dispose();
    }
}
