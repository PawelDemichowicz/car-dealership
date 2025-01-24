package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.business.*;
import pl.zajavka.business.dao.*;
import pl.zajavka.business.management.CarDealershipManagementService;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.database.repository.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;
    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    private CarServiceProcessingService carServiceProcessingService;
    private CarService carService;

    @BeforeEach
    void beforeEach() {
        CarDAO carDAO = new CarRepository();
        SalesmanDAO salesmanDAO = new SalesmanRepository();
        CustomerDAO customerDAO = new CustomerRepository();
        CarServiceRequestDAO carServiceRequestDAO = new CarServiceRequestRepository();
        MechanicDAO mechanicDAO = new MechanicRepository();
        PartDAO partDAO = new PartRepository();
        ServiceDAO serviceDAO = new ServiceRepository();
        ServiceRequestProcessingDAO serviceRequestProcessingDAO = new ServiceRequestProcessingRepository();
        CustomerService customerService = new CustomerService(customerDAO);
        CarService carService = new CarService(carDAO);
        SalesmanService salesmanService = new SalesmanService(salesmanDAO);
        FileDataPreparationService fileDataPreparationService = new FileDataPreparationService();
        MechanicService mechanicService = new MechanicService(mechanicDAO);
        PartCatalogService partCatalogService = new PartCatalogService(partDAO);
        ServiceCatalogService serviceCatalogService = new ServiceCatalogService(serviceDAO);
        this.carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                fileDataPreparationService
        );
        this.carPurchaseService = new CarPurchaseService(
                fileDataPreparationService,
                customerService,
                carService,
                salesmanService
        );
        this.carServiceRequestService = new CarServiceRequestService(
                fileDataPreparationService,
                carService,
                customerService,
                carServiceRequestDAO
        );
        this.carServiceProcessingService = new CarServiceProcessingService(
                fileDataPreparationService,
                mechanicService,
                carService,
                serviceCatalogService,
                partCatalogService,
                carServiceRequestService,
                serviceRequestProcessingDAO
        );
        this.carService = new CarService(
                carDAO
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
        carServiceRequestService.requestService();
    }

    @Test
    @Order(5)
    void processServiceRequest() {
        carServiceProcessingService.process();
    }

    @Test
    @Order(6)
    void printCarHistory() {
        carService.printCarHistory("2C3CDYAG2DH731952");
        carService.printCarHistory("1GCEC19X27Z109567");
    }
}
