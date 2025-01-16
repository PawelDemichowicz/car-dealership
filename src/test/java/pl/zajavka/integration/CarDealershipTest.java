package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.business.CarPurchaseService;
import pl.zajavka.business.CarService;
import pl.zajavka.business.CustomerService;
import pl.zajavka.business.SalesmanService;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.business.dao.SalesmanDAO;
import pl.zajavka.business.management.CarDealershipManagementService;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.CarDealershipManagementRepository;
import pl.zajavka.infrastructure.database.repository.CarRepository;
import pl.zajavka.infrastructure.database.repository.CustomerRepository;
import pl.zajavka.infrastructure.database.repository.SalesmanRepository;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;
    private CarPurchaseService carPurchaseService;

    @BeforeEach
    void beforeEach() {
        CarDAO carDAO = new CarRepository();
        SalesmanDAO salesmanDAO = new SalesmanRepository();
        CustomerDAO customerDAO = new CustomerRepository();
        FileDataPreparationService fileDataPreparationService = new FileDataPreparationService();
        this.carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                fileDataPreparationService
        );
        this.carPurchaseService = new CarPurchaseService(
                fileDataPreparationService,
                new CustomerService(customerDAO),
                new CarService(carDAO),
                new SalesmanService(salesmanDAO)
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
        carPurchaseService.purchase();
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
