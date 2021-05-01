package pt.vstore.stock.dto.mapper;

public interface IDtoMapper<Source, Destiny> {

    Destiny mapToDto(Source src);

    Destiny mapToDto(Source src, Destiny dst);

}
