package inventorymanagementsystem.model;

import java.math.BigDecimal;

/**
 * Plain Java object mapped to the `product` table.
 */
public class Product {

    private int idno;
    private String name;
    private String description;
    private String category;
    private String brand;
    private BigDecimal price;
    private int stock;

    public Product() {
    }

    public Product(String name, String description, String category,
                    String brand, BigDecimal price, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public Product(int idno, String name, String description, String category,
                    String brand, BigDecimal price, int stock) {
        this(name, description, category, brand, price, stock);
        this.idno = idno;
    }

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " (#" + idno + ")";
    }
}
