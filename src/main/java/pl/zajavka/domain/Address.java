package pl.zajavka.domain;

import lombok.*;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "city", "postalCode", "address"})
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String address;
    Customer customer;
}
