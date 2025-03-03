package pl.zajavka.integration.rest;

import org.junit.jupiter.api.Test;
import pl.zajavka.api.dto.CarPurchaseDTO;
import pl.zajavka.api.dto.CarsToBuyDTO;
import pl.zajavka.api.dto.InvoiceDTO;
import pl.zajavka.integration.configuration.RestAssuredIntegrationTestBase;
import pl.zajavka.integration.support.PurchaseControllerTestSupport;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseIT
        extends RestAssuredIntegrationTestBase
        implements PurchaseControllerTestSupport {

    @Test
    void thatCarPurchaseWorksCorrectly() {
        // given
        CarsToBuyDTO carsToBuyDTO = findAvailableCars();
        CarPurchaseDTO carPurchaseDTO = someCarPurchaseDTO();

        // when
        InvoiceDTO invoiceDTO = purchaseCar(carPurchaseDTO);

        // then
        CarsToBuyDTO carsToBuyDTOAfterPurchase = findAvailableCars();

        assertThat(invoiceDTO.getInvoiceNumber()).isNotNull();
        assertThat(invoiceDTO.getDateTime()).isNotNull();
        var carsToBuyBeforePurchase = new ArrayList<>(carsToBuyDTO.getCarsToBuyDTO());
        var carsToBuyAfterPurchase = new ArrayList<>(carsToBuyDTOAfterPurchase.getCarsToBuyDTO());
        carsToBuyBeforePurchase.removeAll(carsToBuyAfterPurchase);
        assertThat(carsToBuyBeforePurchase).hasSize(1);
        assertThat(carsToBuyBeforePurchase.get(0).getVin()).isEqualTo(carPurchaseDTO.getCarVin());
    }

    private static CarPurchaseDTO someCarPurchaseDTO() {
        return CarPurchaseDTO.buildDefaultData()
                .withCarVin("1FT7X2B60FEA74019")
                .withSalesmanPesel("73021314515");
    }
}
