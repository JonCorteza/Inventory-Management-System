package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.ui.UITheme;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.function.IntConsumer;

/** Makes the edit / delete icons in the actions column actually clickable. */
public class TableActionsEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
    private int currentRow;

    public TableActionsEditor(IntConsumer onEdit, IntConsumer onDelete) {
        JButton edit = new JButton("\u270E");
        JButton del = new JButton("\uD83D\uDDD1");
        for (JButton b : new JButton[]{edit, del}) {
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setFocusPainted(false);
            b.setMargin(new Insets(0, 4, 0, 4));
        }
        edit.setForeground(UITheme.ACCENT_GREEN);
        del.setForeground(UITheme.DANGER_RED);
        edit.addActionListener(e -> {
            onEdit.accept(currentRow);
            stopCellEditing();
        });
        del.addActionListener(e -> {
            onDelete.accept(currentRow);
            stopCellEditing();
        });
        panel.add(edit);
        panel.add(del);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row;
        panel.setBackground(UITheme.BG_TABLE_ALT);
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
