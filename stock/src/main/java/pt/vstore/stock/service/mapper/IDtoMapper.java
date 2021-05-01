package pt.vstore.stock.service.mapper;

public interface IDtoMapper<Source, Destiny> {

    Destiny mapToDto(Source src);

    Destiny mapToDto(Source src, Destiny dst);

}
