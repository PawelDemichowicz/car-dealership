package pl.zajavka.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;

@Repository
public interface CarToServiceJpaRepository extends JpaRepository<CarToServiceEntity, Integer> {

    Optional<CarToServiceEntity> findByVin(String vin);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "carServiceRequest",
                    "carServiceRequest.serviceMechanics",
                    "carServiceRequest.serviceMechanics.service",
                    "carServiceRequest.serviceParts",
                    "carServiceRequest.serviceParts.part"
            }
    )
    CarToServiceEntity findCarHistoryByVin(String vin);
}
