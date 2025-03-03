package pl.zajavka.integration.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import pl.zajavka.api.dto.CepikVehicleDTO;
import pl.zajavka.integration.configuration.RestAssuredIntegrationTestBase;
import pl.zajavka.integration.support.CepikControllerTestSupport;
import pl.zajavka.integration.support.WireMockTestSupport;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CepikIT
        extends RestAssuredIntegrationTestBase
        implements CepikControllerTestSupport, WireMockTestSupport {

    Map<String, String> VEHICLE_IDS = Map.of(
            "849930752201839", "pojazd-1.json",
            "3948620503340695", "pojazd-2.json",
            "1545997190537371", "pojazd-3.json",
            "2340228674273462", "pojazd-4.json",
            "4280837150558054", "pojazd-5.json"
    );

    @Test
    void thatFindingRandomVehicleWorksCorrectly() {
        // given
        LocalDate dateFrom = LocalDate.of(2024, 1, 1);
        LocalDate dateTo = LocalDate.of(2024, 6, 30);
        stubForSlowniki(wireMockServer);
        stubForPojazdy(wireMockServer, dateFrom.toString(), dateTo.toString());
        stubForPojazd(wireMockServer);

        // when
        CepikVehicleDTO randomVehicle1 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle2 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle3 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle4 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle5 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle6 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle7 = getCepikRandomVehicle(dateFrom, dateTo);

        Set<CepikVehicleDTO> randomVehicles = new HashSet<>();
        randomVehicles.add(randomVehicle1);
        randomVehicles.add(randomVehicle2);
        randomVehicles.add(randomVehicle3);
        randomVehicles.add(randomVehicle4);
        randomVehicles.add(randomVehicle5);
        randomVehicles.add(randomVehicle6);
        randomVehicles.add(randomVehicle7);

        Assertions.assertThat(randomVehicles).hasSizeGreaterThan(1);
    }

    private void stubForSlowniki(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathMatching("/slowniki/wojewodztwa"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/slowniki-wojewodztwa.json"))
        );
    }

    private void stubForPojazdy(WireMockServer wireMockServer, String dateFrom, String dateTo) {
        Map<String, StringValuePattern> queryParamsPattern = Map.of(
                "data-od", equalTo(dateFrom.replace("-", "")),
                "data-do", equalTo(dateTo.replace("-", ""))
        );
        wireMockServer.stubFor(
                get(urlPathEqualTo("/pojazdy"))
                        .withQueryParams(queryParamsPattern)
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/pojazdy.json"))
        );
    }

    private void stubForPojazd(WireMockServer wireMockServer) {
        VEHICLE_IDS.forEach((vehicleId, fileName) ->
                wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy/%s".formatted(vehicleId)))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/%s".formatted(fileName)))));
    }
}
