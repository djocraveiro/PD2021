package pt.vstore.stock.service.mapper;

public interface IEntityMapper<TSource, TDestiny> {

        TDestiny mapToEntity(TSource src);

        TDestiny updateEntity(TSource src, TDestiny dst);

}