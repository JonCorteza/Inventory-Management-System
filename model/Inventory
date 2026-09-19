package inventorymanagementsystem.model;

import java.sql.Date;

/**
 * Maps to the `inventory` table. A row represents a single stock
 * movement: a positive quantity is stock received from a supplier,
 * a negative quantity is stock removed/sold.
 */
public class Inventory {

    private int inventoryId;
    private int productId;
    private int supplierId;
    private int quantity;
    private Date transactionDate;

    // Convenience fields populated by JOIN queries for display only
    // (they are not real columns on the `inventory` table).
    private String productName;
    private String supplierName;

    public Inventory() {
    }

    public Inventory(int productId, int supplierId, int quantity, Date transactionDate) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
