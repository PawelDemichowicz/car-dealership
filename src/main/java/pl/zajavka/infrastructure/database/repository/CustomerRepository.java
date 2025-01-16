package pl.zajavka.infrastructure.database.repository;

import org.hibernate.Session;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

import java.util.Objects;
import java.util.Optional;

public class CustomerRepository implements CustomerDAO {

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            String query = "SELECT cu FROM CustomerEntity cu WHERE cu.email = :email";
            Optional<CustomerEntity> result = session.createQuery(query, CustomerEntity.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();

            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void issueInvoice(CustomerEntity customer) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            if (Objects.isNull(customer.getCustomerId())) {
                session.persist(customer);
            }
            customer.getInvoices().stream()
                    .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                    .forEach(invoice -> {
                        invoice.setCustomer(customer);
                        session.persist(invoice);
                    });
            session.getTransaction().commit();
        }
    }
}
