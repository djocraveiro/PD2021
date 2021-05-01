package pt.vstore.stock.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.vstore.stock.model.entity.ProductEntity;

import java.util.*;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("select p from ProductEntity p where p.id = :id")
    Optional<ProductEntity> findById(UUID id);

}
