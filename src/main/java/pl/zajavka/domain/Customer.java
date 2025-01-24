package pl.zajavka.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "customerId")
@ToString(of = {"customerId", "name", "surname", "email"})
public class Customer {
    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;
    Set<Invoice> invoices;
    Set<CarServiceRequest> carServiceRequests;
}
