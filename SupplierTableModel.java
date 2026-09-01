package inventorymanagementsystem.ui.component;

import inventorymanagementsystem.model.Supplier;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/** Backs the supplier JTable on the Report Generation screen. */
public class SupplierTableModel extends AbstractTableModel {

    public static final String[] COLUMNS = {
        "ID", "SUPPLIER NAME", "CONTACT PERSON", "PHONE", "ADDRESS"
    };

    private List<Supplier> rows = new ArrayList<>();

    public void setRows(List<Supplier> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    public Supplier getSupplierAt(int row) {
        return rows.get(row);
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
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Supplier s = rows.get(row);
        switch (col) {
            case 0:
                return s.getSupplierId();
            case 1:
                return s.getSupplierName();
            case 2:
                return s.getContactPerson();
            case 3:
                return s.getPhone();
            case 4:
                return s.getAddress();
            default:
                return null;
        }
    }
}
