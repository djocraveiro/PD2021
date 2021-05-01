package pt.vstore.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.vstore.stock.dto.Product;
import pt.vstore.stock.service.mapper.ProductMapper;
import pt.vstore.stock.exception.ProductNotFoundException;
import pt.vstore.stock.model.entity.ProductEntity;
import pt.vstore.stock.model.repository.IProductRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ProductMapper productMapper;


    public Product findById(final UUID id) {
        ProductEntity product = findProductById(id);
        return productMapper.mapToDto(product);
    }

    public Collection<Product> findProducts() {
        Collection<ProductEntity> productList = repository.findAll();

        return productList.stream()
                .map(p-> productMapper.mapToDto(p))
                .collect(Collectors.toList());
    }

    public Page<Product> findProducts(Pageable pageable) {
        Page<ProductEntity> page = repository.findAll(pageable);

        List<Product> result = page.getContent().stream()
                .map(p-> productMapper.mapToDto(p))
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    public Product createProduct(final Product prod) {
        ProductEntity product = productMapper.mapToEntity(prod);
        product = repository.save(product);

        prod.setId(product.getId());
        return prod;
    }

    public void updateProduct(final Product prod) {
        ProductEntity product = findProductById(prod.getId());

        product = productMapper.updateEntity(prod, product);
        repository.save(product);
    }

    public void deleteProduct(final UUID id) {
        ProductEntity product = findProductById(id);

        repository.delete(product);
    }


    private ProductEntity findProductById(final UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

}
