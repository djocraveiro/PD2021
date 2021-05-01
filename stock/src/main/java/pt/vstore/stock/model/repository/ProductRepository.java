package pt.vstore.stock.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.vstore.stock.model.entity.ProductEntity;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Repository
public class ProductRepository implements IRepository<ProductEntity> {

    //TODO: review this
    private Map<String, ProductEntity> products = new HashMap<>();


    public Optional<ProductEntity> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public void add(ProductEntity product) {
        products.put(product.getId(), product);
    }

    public Collection<ProductEntity> findAll() {
        return products.values();
    }

    public Page<ProductEntity> findAll(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();

        List<ProductEntity> result = products.values()
                .stream()
                .skip(toSkip)
                .limit(pageable.getPageSize())
                .collect(toList());

        return new PageImpl<>(result, pageable, products.size());
    }

    @Override
    public void update(ProductEntity product) {
        products.remove(product.getId());
        products.put(product.getId(), product);
    }

    @Override
    public void delete(ProductEntity product) {
        products.remove(product.getId());
    }
}
