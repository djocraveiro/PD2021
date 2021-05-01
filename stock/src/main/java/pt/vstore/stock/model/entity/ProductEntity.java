package pt.vstore.stock.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "product", schema = "public")
public class ProductEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "available", nullable = false)
    private int available;

    @Column(name = "price", nullable = false)
    private float price;


    public ProductEntity() {
        this.id = null;
        this.name = "";
        this.description = "";
        this.available = 0;
        this.price = 0;
    }


    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getAvailable() { return available; }

    public void setAvailable(int available) { this.available = available; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }


    @Override
    public boolean isNew() {
        return id == null;
    }


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
