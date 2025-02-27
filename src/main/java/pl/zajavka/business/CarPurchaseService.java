package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarPurchaseService {

    private final CustomerService customerService;
    private final CarService carService;
    private final SalesmanService salesmanService;

    public List<CarToBuy> availableCars() {
        return carService.findAvailableCars();
    }

    public List<Salesman> availableSalesman() {
        return salesmanService.findAvailableSalesmen();
    }

    @Transactional
    public Invoice purchase(final CarPurchaseRequest request) {
        return existingCustomerEmailExists(request.getExistingCustomerEmail())
                ? processNextTimeToBuyCustomer(request)
                : processFirstTimeToBuyCustomer(request);
    }

    private boolean existingCustomerEmailExists(String email) {
        return Objects.nonNull(email) && !email.isBlank();
    }

    private Invoice processFirstTimeToBuyCustomer(CarPurchaseRequest request) {
        CarToBuy car = carService.findCarToBuy(request.getCarVin());
        Salesman salesman = salesmanService.findSalesman(request.getSalesmanPesel());
        Invoice invoice = buildInvoice(car, salesman);

        Customer customer = buildCustomer(request, invoice);
        customerService.issueInvoice(customer);
        return invoice;
    }

    private Invoice processNextTimeToBuyCustomer(CarPurchaseRequest request) {
        Customer existingCustomer = customerService.findCustomer(request.getCustomerEmail());
        CarToBuy car = carService.findCarToBuy(request.getCarVin());
        Salesman salesman = salesmanService.findSalesman(request.getSalesmanPesel());
        Invoice invoice = buildInvoice(car, salesman);
        Set<Invoice> existingInvoices = existingCustomer.getInvoices();
        existingInvoices.add(invoice);
        customerService.issueInvoice(existingCustomer.withInvoices(existingInvoices));
        return invoice;
    }

    public Customer buildCustomer(CarPurchaseRequest request, Invoice invoice) {
        return Customer.builder()
                .name(request.getCustomerName())
                .surname(request.getCustomerSurname())
                .phone(request.getCustomerPhone())
                .email(request.getCustomerEmail())
                .address(Address.builder()
                        .country(request.getCustomerAddressCountry())
                        .city(request.getCustomerAddressCity())
                        .postalCode(request.getCustomerAddressPostalCode())
                        .address(request.getCustomerAddressStreet())
                        .build())
                .invoices(Set.of(invoice))
                .build();
    }

    private Invoice buildInvoice(CarToBuy car, Salesman salesman) {
        return Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .dateTime(OffsetDateTime.now())
                .car(car)
                .salesman(salesman)
                .build();
    }
}
