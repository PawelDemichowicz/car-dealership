package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.Invoice;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "carToBuyId")
@ToString(of = {"carToBuyId", "vin", "brand", "model", "year"})
public class CarToBuy {

    Integer carToBuyId;
    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
    Invoice invoice;
}
