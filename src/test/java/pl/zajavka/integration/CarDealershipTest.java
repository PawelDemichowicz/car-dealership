package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.infrastructure.configuration.HibernateUtil;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        log.info("");
    }

    @Test
    @Order(2)
    void init() {

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
