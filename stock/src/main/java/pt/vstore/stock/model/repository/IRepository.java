package pt.vstore.stock.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.vstore.stock.model.entity.ProductEntity;

import java.util.Collection;
import java.util.Optional;

public interface IRepository<TEntity> {

    Optional<ProductEntity> findById(String id);

    void add(ProductEntity product);

    Collection<ProductEntity> findAll();

    Page<ProductEntity> findAll(Pageable pageable);

    void update(ProductEntity product);

    void delete(ProductEntity product);

}
