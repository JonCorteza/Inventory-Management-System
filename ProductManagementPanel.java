package inventorymanagementsystem.ui;

import inventorymanagementsystem.dao.ProductDAO;
import inventorymanagementsystem.model.Product;
import inventorymanagementsystem.ui.component.ProductTableModel;
import inventorymanagementsystem.ui.component.StockLevelRenderer;
import inventorymanagementsystem.ui.component.TableActionsEditor;
import inventorymanagementsystem.ui.component.TableActionsRenderer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Recreates the "Product Management" screen from the mockup: search bar,
 * Add/Update/Delete actions, and a paginated product table with per-row
 * edit/delete icons.
 */
public class ProductManagementPanel extends JPanel {

    private final ProductDAO productDAO = new ProductDAO();
    private final ProductTableModel tableModel = new ProductTableModel();
    private final JTable table = new JTable(tableModel);
    private final JTextField searchField = new JTextField();
    private final JLabel pageInfoLabel = new JLabel();
    private final JLabel pageNumbersLabel = new JLabel();

    private int currentPage = 1;
    private final int pageSize = 10;
    private int totalCount = 0;

    public ProductManagementPanel() {
        setLayout(new BorderLayout(0, 14));
        setOpaque(false);
        setBorder(new EmptyBorder(6, 0, 0, 0));

        add(buildToolbar(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildPagination(), BorderLayout.SOUTH);

        reload();
    }

    private JComponent buildToolbar() {
        JPanel bar = new JPanel(new BorderLayout(12, 0));
        bar.setOpaque(false);

        UITheme.styleTextField(searchField);
        searchField.putClientProperty("JTextField.placeholderText", "Search products...");
        searchField.addActionListener(e -> {
            currentPage = 1;
            reload();
        });
        bar.add(searchField, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton add = new JButton("+ ADD PRODUCT");
        UITheme.stylePrimaryButton(add);
        add.addActionListener(e -> openAddDialog());

        JButton update = new JButton("\u270E UPDATE PRODUCT");
        UITheme.styleSecondaryButton(update);
        update.addActionListener(e -> openUpdateDialogForSelection());

        JButton delete = new JButton("\uD83D\uDDD1 DELETE PRODUCT");
        UITheme.styleDangerButton(delete);
        delete.addActionListener(e -> deleteSelection());

        actions.add(add);
        actions.add(update);
        actions.add(delete);

        bar.add(actions, BorderLayout.EAST);
        return bar;
    }

    private JComponent buildTable() {
        table.setRowHeight(34);
        table.setBackground(UITheme.BG_PANEL);
        table.setForeground(UITheme.TEXT_PRIMARY);
        table.setGridColor(new Color(0x22, 0x22, 0x22));
        table.setSelectionBackground(UITheme.BG_TABLE_ALT);
        table.setSelectionForeground(UITheme.TEXT_PRIMARY);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.BG_PANEL);
        header.setForeground(UITheme.ACCENT_GREEN);
        header.setFont(UITheme.FONT_BOLD);
        header.setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setMaxWidth(36);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(220);
        table.getColumnModel().getColumn(7).setCellRenderer(new StockLevelRenderer(20, 5));

        table.getColumnModel().getColumn(8).setMaxWidth(70);
        table.getColumnModel().getColumn(8).setCellRenderer(new TableActionsRenderer());
        table.getColumnModel().getColumn(8).setCellEditor(new TableActionsEditor(
                this::openUpdateDialogForRow,
                this::deleteRow
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(UITheme.BG_PANEL);
        scroll.setBorder(UITheme.cardBorder());
        return scroll;
    }

    private JComponent buildPagination() {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        pageInfoLabel.setForeground(UITheme.TEXT_MUTED);
        pageInfoLabel.setFont(UITheme.FONT_BODY);
        row.add(pageInfoLabel, BorderLayout.WEST);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        nav.setOpaque(false);

        JButton prev = linkButton("Prev");
        prev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                reload();
            }
        });

        pageNumbersLabel.setForeground(UITheme.TEXT_PRIMARY);
        pageNumbersLabel.setFont(UITheme.FONT_BOLD);

        JButton next = linkButton("Next");
        next.addActionListener(e -> {
            int maxPage = Math.max(1, (int) Math.ceil(totalCount / (double) pageSize));
            if (currentPage < maxPage) {
                currentPage++;
                reload();
            }
        });

        nav.add(prev);
        nav.add(pageNumbersLabel);
        nav.add(next);
        row.add(nav, BorderLayout.EAST);
        return row;
    }

    private JButton linkButton(String text) {
        JButton b = new JButton(text);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setForeground(UITheme.TEXT_MUTED);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    /** Re-queries the database for the current page + search term and refreshes the table. */
    public void reload() {
        String term = searchField.getText();
        List<Product> products = productDAO.getProducts(currentPage, pageSize, term);
        totalCount = productDAO.getTotalCount(term);
        tableModel.setRows(products);

        int from = totalCount == 0 ? 0 : (currentPage - 1) * pageSize + 1;
        int to = Math.min(currentPage * pageSize, totalCount);
        pageInfoLabel.setText("Showing " + from + "-" + to + " of " + totalCount + " products");

        int maxPage = Math.max(1, (int) Math.ceil(totalCount / (double) pageSize));
        pageNumbersLabel.setText(currentPage + " / " + maxPage);
    }

    private void openAddDialog() {
        ProductDialog dialog = new ProductDialog((Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            productDAO.insert(dialog.getResult());
            reload();
        }
    }

    private void openUpdateDialogForSelection() {
        Set<Integer> checked = tableModel.getCheckedIds();
        if (checked.size() != 1) {
            JOptionPane.showMessageDialog(this, "Select exactly one product to update.",
                    "Update Product", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idno = checked.iterator().next();
        openUpdateDialogForProduct(productDAO.getById(idno));
    }

    private void openUpdateDialogForRow(int row) {
        openUpdateDialogForProduct(tableModel.getProductAt(row));
    }

    private void openUpdateDialogForProduct(Product existing) {
        if (existing == null) {
            return;
        }
        ProductDialog dialog = new ProductDialog((Frame) SwingUtilities.getWindowAncestor(this), existing);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            productDAO.update(dialog.getResult());
            reload();
        }
    }

    private void deleteSelection() {
        Set<Integer> checked = tableModel.getCheckedIds();
        if (checked.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one product to delete.",
                    "Delete Product", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        confirmAndDelete(new ArrayList<>(checked));
    }

    private void deleteRow(int row) {
        confirmAndDelete(Collections.singletonList(tableModel.getProductAt(row).getIdno()));
    }

    private void confirmAndDelete(List<Integer> ids) {
        int choice = JOptionPane.showConfirmDialog(this,
                "Delete " + ids.size() + " product(s)? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            productDAO.deleteMultiple(ids);
            tableModel.clearChecked();
            reload();
        }
    }
}
