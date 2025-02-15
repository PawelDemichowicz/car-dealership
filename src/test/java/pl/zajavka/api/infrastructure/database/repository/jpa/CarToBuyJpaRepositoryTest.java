package pl.zajavka.api.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.repository.jpa.CarToBuyJpaRepository;
import pl.zajavka.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.List;

import static pl.zajavka.util.EntityFixtures.*;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
public class CarToBuyJpaRepositoryTest {

    private CarToBuyJpaRepository carToBuyJpaRepository;

    @Test
    void thatCarCanBeSavedCorrectly() {
        // given
        List<CarToBuyEntity> cars = List.of(someCar1(), someCar2(), someCar3());
        carToBuyJpaRepository.saveAll(cars);

        // when
        List<CarToBuyEntity> availableCars = carToBuyJpaRepository.findAvailableCars();

        // then
        Assertions.assertThat(availableCars).hasSize(9);
    }
}
