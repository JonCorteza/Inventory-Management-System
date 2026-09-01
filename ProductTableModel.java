package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.model.Product;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Backs the product JTable. Column 0 is a checkbox (row selection for
 * bulk delete), the last column is a virtual "actions" column rendered
 * with edit/delete icon buttons.
 */
public class ProductTableModel extends AbstractTableModel {

    public static final String[] COLUMNS = {
        "", "ID NO", "PRODUCT NAME", "DESCRIPTION", "CATEGORY", "BRAND", "PRICE (₱)", "STOCK LEVEL", ""
    };

    private List<Product> rows = new ArrayList<>();
    private final Set<Integer> checkedIds = new HashSet<>();

    public void setRows(List<Product> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    public Product getProductAt(int row) {
        return rows.get(row);
    }

    public Set<Integer> getCheckedIds() {
        return checkedIds;
    }

    public void clearChecked() {
        checkedIds.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int col) {
        return COLUMNS[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
                return Boolean.class;
            case 1:
            case 7:
                return Integer.class;
            case 6:
                return BigDecimal.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0 || col == 8; // checkbox + actions column
    }

    @Override
    public Object getValueAt(int row, int col) {
        Product p = rows.get(row);
        switch (col) {
            case 0:
                return checkedIds.contains(p.getIdno());
            case 1:
                return p.getIdno();
            case 2:
                return p.getName();
            case 3:
                return p.getDescription();
            case 4:
                return p.getCategory();
            case 5:
                return p.getBrand();
            case 6:
                return p.getPrice();
            case 7:
                return p.getStock();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            Product p = rows.get(row);
            boolean checked = Boolean.TRUE.equals(value);
            if (checked) {
                checkedIds.add(p.getIdno());
            } else {
                checkedIds.remove(p.getIdno());
            }
            fireTableCellUpdated(row, col);
        }
    }
}
