package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.CarToBuyDAO;
import pl.zajavka.business.dao.CarToServiceDAO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CarService {
    private final CarToBuyDAO carToBuyDAO;

    private final CarToServiceDAO carToServiceDAO;

    @Transactional
    public List<CarToBuy> findAvailableCars() {
        List<CarToBuy> availableCars = carToBuyDAO.findAvailable();
        log.info("Available cars: [{}]", availableCars.size());
        return availableCars;
    }

    @Transactional
    public CarToBuy findCarToBuy(String vin) {
        Optional<CarToBuy> carToBuyByVin = carToBuyDAO.findCarToBuyByVin(vin);
        if (carToBuyByVin.isEmpty()) {
            throw new RuntimeException("Could not find car by vin: [%s]".formatted(vin));
        }
        return carToBuyByVin.get();
    }

    @Transactional
    public Optional<CarToService> findCarToService(String vin) {
        return carToServiceDAO.findCarToServiceByVin(vin);
    }

    @Transactional
    public CarToService saveCarToService(CarToBuy carToBuy) {
        CarToService carToService = CarToService.builder()
                .vin(carToBuy.getVin())
                .brand(carToBuy.getBrand())
                .model(carToBuy.getModel())
                .year(carToBuy.getYear())
                .build();
        return carToServiceDAO.saveCarToService(carToService);
    }

    @Transactional
    public CarToService saveCarToService(CarToService carToService) {
        return carToServiceDAO.saveCarToService(carToService);
    }

    @Transactional
    public void printCarHistory(String vin) {
        CarHistory carHistory = carToServiceDAO.findCarHistoryByVin(vin);
        log.info("###CAR HISTORY FOR VIN: [{}]", vin);
        carHistory.getCarServiceRequests().forEach(this::printServiceRequest);
    }

    private void printServiceRequest(CarHistory.CarServiceRequest serviceRequest) {
        log.info("###SERVICE REQUEST: [{}]", serviceRequest);
        serviceRequest.getServices().forEach(service -> log.info("###SERVICE: [{}]", service));
        serviceRequest.getParts().forEach(part -> log.info("###PART: [{}]", part));
    }
}
