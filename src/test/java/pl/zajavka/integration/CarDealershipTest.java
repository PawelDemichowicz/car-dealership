package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.business.management.CarDealershipManagementService;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.CarDealershipManagementRepository;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;

    @BeforeEach
    void beforeEach() {
        this.carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                new FileDataPreparationService()
        );
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        carDealershipManagementService.purge();
    }

    @Test
    @Order(2)
    void init() {
        carDealershipManagementService.init();
    }

    @Test
    @Order(3)
    void purchase() {

    }

    @Test
    @Order(4)
    void makeServiceRequest() {

    }

    @Test
    @Order(5)
    void processServiceRequest() {

    }

    @Test
    @Order(6)
    void printCarHistory() {

    }
}
