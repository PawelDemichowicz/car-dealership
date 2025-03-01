package pl.zajavka.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.zajavka.api.dto.CarPurchaseDTO;
import pl.zajavka.api.dto.InvoiceDTO;
import pl.zajavka.api.dto.mapper.CarPurchaseMapper;
import pl.zajavka.api.dto.mapper.InvoiceMapper;
import pl.zajavka.business.CarPurchaseService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PurchaseRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class PurchaseRestControllerWebMvcTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockitoBean
    @SuppressWarnings("unused")
    private CarPurchaseService carPurchaseService;
    @MockitoBean
    @SuppressWarnings("unused")
    private CarPurchaseMapper carPurchaseMapper;
    @MockitoBean
    @SuppressWarnings("unused")
    private InvoiceMapper invoiceMapper;

    @Test
    void carPurchaseWorksCorrectly() throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData();
        InvoiceDTO someInvoiceDTO = someInvoiceDTO();
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);
        String responseBody = objectMapper.writeValueAsString(someInvoiceDTO);

        when(invoiceMapper.map(any())).thenReturn(someInvoiceDTO);

        // when, then
        MvcResult result = mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                        .content(objectMapper.writeValueAsString(carPurchaseBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO.getInvoiceNumber())))
                .andExpect(jsonPath("$.dateTime", Matchers.is(someInvoiceDTO.getDateTime().toString())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerEmail("badEmail");
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        // when, then
        mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", Matchers.notNullValue()));

    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerPhone(phone);
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        // when, then
        if (correctPhone) {
            InvoiceDTO someInvoiceDTO = someInvoiceDTO();
            String responseBody = objectMapper.writeValueAsString(someInvoiceDTO);
            when(invoiceMapper.map(any())).thenReturn(someInvoiceDTO);

            MvcResult result = mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO.getInvoiceNumber())))
                    .andExpect(jsonPath("$.dateTime", Matchers.is(someInvoiceDTO.getDateTime().toString())))
                    .andReturn();

            assertThat(result.getResponse().getContentAsString())
                    .isEqualTo(responseBody);
        } else {
            mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorId", Matchers.notNullValue()));
        }
    }

    private InvoiceDTO someInvoiceDTO() {
        return InvoiceDTO.builder()
                .invoiceNumber("invNumb")
                .dateTime(OffsetDateTime.of(
                        2024,
                        10,
                        10,
                        10,
                        30,
                        15,
                        0,
                        ZoneOffset.UTC))
                .build();
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "+4812504203260"),
                Arguments.of(false, "4812504203260")
        );
    }
}
