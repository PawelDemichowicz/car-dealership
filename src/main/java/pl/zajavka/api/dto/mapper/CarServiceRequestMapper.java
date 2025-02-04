package pl.zajavka.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.zajavka.api.dto.CarServiceCustomerRequestDTO;
import pl.zajavka.api.dto.CarServiceMechanicProcessingUnitDTO;
import pl.zajavka.api.dto.CarServiceRequestDTO;
import pl.zajavka.domain.*;

@Mapper(componentModel = "spring")
public interface CarServiceRequestMapper {

    default CarServiceRequest map(CarServiceCustomerRequestDTO dto) {
        if (dto.isNewCarCandidate()) {
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .name(dto.getCustomerName())
                            .surname(dto.getCustomerSurname())
                            .phone(dto.getCustomerPhone())
                            .email(dto.getCustomerEmail())
                            .address(Address.builder()
                                    .country(dto.getCustomerAddressCountry())
                                    .city(dto.getCustomerAddressCity())
                                    .postalCode(dto.getCustomerAddressPostalCode())
                                    .address(dto.getCustomerAddressStreet())
                                    .build())
                            .build())
                    .car(CarToService.builder()
                            .vin(dto.getCarVin())
                            .brand(dto.getCarBrand())
                            .model(dto.getCarModel())
                            .year(dto.getCarYear())
                            .build())
                    .customerComment(dto.getCustomerComment())
                    .build();
        } else {
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .email(dto.getExistingCustomerEmail())
                            .build())
                    .car(CarToService.builder()
                            .vin(dto.getExistingCarVin())
                            .build())
                    .customerComment(dto.getCustomerComment())
                    .build();
        }
    }

    CarServiceRequestDTO map(CarServiceRequest request);

    CarServiceProcessingRequest map(CarServiceMechanicProcessingUnitDTO dto);
}
