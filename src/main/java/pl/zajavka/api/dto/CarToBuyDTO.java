package pl.zajavka.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarToBuyDTO {

    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
}
