package pt.vstore.stock.exception;

import java.util.UUID;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
        super("Could not find product " + id);
    }

}
