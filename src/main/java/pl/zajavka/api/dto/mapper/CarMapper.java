package pl.zajavka.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.zajavka.api.dto.CarToBuyDTO;
import pl.zajavka.domain.CarToBuy;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarToBuyDTO map(final CarToBuy car);
}
