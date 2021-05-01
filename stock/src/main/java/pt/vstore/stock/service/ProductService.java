package pt.vstore.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.vstore.stock.dto.Product;
import pt.vstore.stock.dto.mapper.ProductMapper;
import pt.vstore.stock.exception.ProductNotFoundException;
import pt.vstore.stock.model.entity.ProductEntity;
import pt.vstore.stock.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper productMapper;


    public Product findById(final String id) {
        ProductEntity product = findProductById(id);
        return productMapper.mapToDto(product);
    }

    public Collection<Product> findProducts() {
        Collection<ProductEntity> productList = repository.findAll();

        List<Product> result = new ArrayList<>(productList.size());
        productList.forEach(p -> result.add(productMapper.mapToDto(p)));

        return result;
    }

    public Page<Product> findProducts(Pageable pageable) {
        Page<ProductEntity> page = repository.findAll(pageable);

        List<Product> result = new ArrayList<>(page.getNumberOfElements());
        page.getContent().forEach(p -> result.add(productMapper.mapToDto(p)));

        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    public Product createProduct(final Product prod) {
        ProductEntity product = productMapper.mapToEntity(prod);
        repository.add(product);

        prod.setId(prod.getId());
        return prod;
    }

    public void updateProduct(final Product prod) {
        ProductEntity product = findProductById(prod.getId());

        product = productMapper.mapToEntity(prod, product);
        repository.update(product);
    }

    public void deleteProduct(final String id) {
        ProductEntity product = findProductById(id);

        repository.delete(product);
    }


    private ProductEntity findProductById(String id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }

}
