package pt.vstore.stock.dto.mapper;

public interface IEntityMapper<TSource, TDestiny> {

        TDestiny mapToEntity(TSource src);

        TDestiny mapToEntity(TSource src, TDestiny dst);

}