package pt.vstore.stock.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ProductEntity {

    @Id //TODO: generate id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available", nullable = false)
    private int available;

    @Column(name = "price", nullable = false)
    private float price;


    protected ProductEntity() {
        this.name = "";
        this.description = "";
        this.available = 0;
        this.price = 0;
    }

    public ProductEntity(String id) {
        this();

        this.id = id;
    }


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getAvailable() { return available; }

    public void setAvailable(int available) { this.available = available; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity product = (ProductEntity) o;
        return id.equals(product.id) && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Product { id='" + id + "'" + ", name='" + name + "' }";
    }
}
