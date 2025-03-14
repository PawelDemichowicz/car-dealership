package pl.zajavka.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.zajavka.api.dto.InvoiceDTO;
import pl.zajavka.domain.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceDTO map(Invoice invoice);
}
