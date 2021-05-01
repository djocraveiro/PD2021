package pt.vstore.stock.dto;

import javax.validation.constraints.*;
import java.util.UUID;

public class Product {

    private UUID id;

    @NotNull
    @NotBlank
    @Size(min = 0, max = 50)
    private String name;

    @Size(min = 0, max = 500)
    private String description;

    @NotNull
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private int available;

    @NotNull
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private float price;

    //TODO: product tags


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
}
