package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Objects;
import java.util.Optional;

public class CarRepository implements CarDAO {

    @Override
    public Optional<CarToBuyEntity> findCarToBuyByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToBuyEntity> criteriaQuery = criteriaBuilder.createQuery(CarToBuyEntity.class);
            Root<CarToBuyEntity> root = criteriaQuery.from(CarToBuyEntity.class);

            ParameterExpression<String> param1 = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), param1));

            Query<CarToBuyEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(param1, vin);
            try {
                CarToBuyEntity result = query.getSingleResult();
                session.getTransaction().commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                return Optional.empty();
            }
        }
    }
}
