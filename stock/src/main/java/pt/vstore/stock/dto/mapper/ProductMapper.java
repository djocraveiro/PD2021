package pt.vstore.stock.dto.mapper;

import org.springframework.stereotype.Service;
import pt.vstore.stock.dto.Product;
import pt.vstore.stock.model.entity.ProductEntity;

@Service
public class ProductMapper implements
        IDtoMapper<ProductEntity, Product>,
        IEntityMapper<Product, ProductEntity>{

    @Override
    public Product mapToDto(ProductEntity src) {
        return mapToDto(src, new Product());
    }

    @Override
    public Product mapToDto(ProductEntity src, Product dst) {
        dst.setId(src.getId());
        dst.setName(src.getName());
        dst.setDescription(src.getDescription());
        dst.setAvailable(src.getAvailable());
        dst.setPrice(src.getPrice());
        return dst;
    }


    @Override
    public ProductEntity mapToEntity(Product src) {
        ProductEntity dst = new ProductEntity(src.getId());
        return mapToEntity(src, dst);
    }

    @Override
    public ProductEntity mapToEntity(Product src,
                                     ProductEntity dst) {

        dst.setName(src.getName());
        dst.setDescription(src.getDescription());
        dst.setAvailable(src.getAvailable());
        dst.setPrice(src.getPrice());
        return dst;
    }
}
