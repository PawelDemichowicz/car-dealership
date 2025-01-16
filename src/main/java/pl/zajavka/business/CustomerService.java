package pl.zajavka.business;

import lombok.AllArgsConstructor;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

import java.util.Optional;

@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    public void issueInvoice(CustomerEntity customer) {
        customerDAO.issueInvoice(customer);
    }

    public CustomerEntity findCustomer(String email) {
        Optional<CustomerEntity> customer = customerDAO.findByEmail(email);
        if (customer.isEmpty()) {
            throw new RuntimeException("Could not find customer by email: [%s]".formatted(email));
        }
        return customer.get();
    }
}
