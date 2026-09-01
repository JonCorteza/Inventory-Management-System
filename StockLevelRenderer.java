package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.ui.UITheme;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

 // Stock Level indicator color 1 ≤ stock ≤ 5 = low level | stock 6 ≤ stock ≤ 20 = mid level | stock ≥ 21 green color = good level
 
public class StockLevelRenderer extends DefaultTableCellRenderer {

    private final int lowThreshold;
    private final int criticalThreshold;

    public StockLevelRenderer(int lowThreshold, int criticalThreshold) {
        this.lowThreshold = lowThreshold;
        this.criticalThreshold = criticalThreshold;
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int stock = (value instanceof Integer) ? (Integer) value : 0;

        Color dot = stock <= criticalThreshold ? UITheme.DANGER_RED
                : stock <= lowThreshold ? UITheme.WARNING_YELLOW
                : UITheme.ACCENT_GREEN;

        label.setIcon(new DotIcon(dot, 9));
        label.setIconTextGap(8);
        label.setText(String.valueOf(stock));
        label.setForeground(UITheme.TEXT_PRIMARY);
        label.setOpaque(true);
        label.setBackground(isSelected ? UITheme.BG_TABLE_ALT : table.getBackground());
        return label;
    }

    /** Small filled circle icon drawn in code - no image asset needed. */
    private static class DotIcon implements Icon {
        private final Color color;
        private final int size;

        DotIcon(Color color, int size) {
            this.color = color;
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillOval(x, y + 3, size, size);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }
}
