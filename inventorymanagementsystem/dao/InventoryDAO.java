package inventorymanagementsystem.dao;

import inventorymanagementsystem.db.DBConnection;
import inventorymanagementsystem.model.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data-access class for the `inventory` table (stock movements), plus
 * the aggregate queries that feed the Reporting Dashboard cards.
 */
public class InventoryDAO {

    /**
     * Records a stock movement AND applies it to product.stock in a
     * single database transaction, so the two tables never fall out of
     * sync. Use a positive quantity for stock received, negative for
     * stock removed/sold.
     */
    public boolean recordTransaction(Inventory inv) {
        String insertSql = "INSERT INTO inventory (product_id, supplier_id, quantity, transaction_date) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE product SET stock = stock + ? WHERE idno = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, inv.getProductId());
                ps.setInt(2, inv.getSupplierId());
                ps.setInt(3, inv.getQuantity());
                ps.setDate(4, inv.getTransactionDate());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(updateStockSql)) {
                ps.setInt(1, inv.getQuantity());
                ps.setInt(2, inv.getProductId());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Joined with product/supplier names - feeds the "Recent Transaction" timeline. */
    public List<Inventory> getRecentTransactions(int limit) {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT i.inventory_id, i.product_id, i.supplier_id, i.quantity, i.transaction_date, "
                + "p.name AS product_name, s.supplier_name "
                + "FROM inventory i "
                + "LEFT JOIN product p ON i.product_id = p.idno "
                + "LEFT JOIN supplier s ON i.supplier_id = s.supplier_id "
                + "ORDER BY i.transaction_date DESC, i.inventory_id DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Inventory inv = new Inventory();
                    inv.setInventoryId(rs.getInt("inventory_id"));
                    inv.setProductId(rs.getInt("product_id"));
                    inv.setSupplierId(rs.getInt("supplier_id"));
                    inv.setQuantity(rs.getInt("quantity"));
                    inv.setTransactionDate(rs.getDate("transaction_date"));
                    inv.setProductName(rs.getString("product_name"));
                    inv.setSupplierName(rs.getString("supplier_name"));
                    list.add(inv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Sums outgoing stock (negative-quantity rows, i.e. units sold/removed)
     * grouped by product category - feeds the "Top Selling Categories" chart.
     */
    public Map<String, Integer> getTopSellingCategories(int limit) {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT p.category, SUM(-i.quantity) AS units_sold "
                + "FROM inventory i JOIN product p ON i.product_id = p.idno "
                + "WHERE i.quantity < 0 "
                + "GROUP BY p.category ORDER BY units_sold DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getString("category"), rs.getInt("units_sold"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTotalUnitsSold() {
        String sql = "SELECT COALESCE(SUM(-quantity), 0) FROM inventory WHERE quantity < 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
