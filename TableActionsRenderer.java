package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.ui.UITheme;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

/** Renders the small edit / delete icon pair at the end of each product row. */
public class TableActionsRenderer extends JPanel implements TableCellRenderer {

    public TableActionsRenderer() {
        super(new FlowLayout(FlowLayout.CENTER, 6, 0));
        setOpaque(true);
        add(iconLabel("\u270E", UITheme.TEXT_MUTED));
        add(iconLabel("\uD83D\uDDD1", UITheme.TEXT_MUTED));
    }

    private JLabel iconLabel(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        return l;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setBackground(isSelected ? UITheme.BG_TABLE_ALT : table.getBackground());
        return this;
    }
}
