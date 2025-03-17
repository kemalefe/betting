package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.BetSlipService;
import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.contract.BetType;
import com.bilyoner.betting.domain.core.CustomerDto;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BetSlipController.class)
class BetSlipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BetSlipService betSlipService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void initializeBetSlip_ShouldReturnResponseEntity() throws Exception {

        BetSlipDto betSlipDto = new BetSlipDto();
        betSlipDto.setEventId(1L);
        betSlipDto.setBetAmount(new BigDecimal("50.00"));
        betSlipDto.setBetOdds(new BigDecimal("1.67"));
        betSlipDto.setBetType(BetType.AWAY_WIN);
        betSlipDto.setCouponCount(200);
        betSlipDto.setCurrencyCode("TL");

        BetSlipInitializeResponse expectedResponse = new BetSlipInitializeResponse();

        when(betSlipService.initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bet-slip/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betSlipDto))
                        .header("x-customer-id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(betSlipService, atLeast(1)).initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class));
    }

    @Test
    void initializeBetSlip_ShouldReturnBadRequest_WhenBetAmountExceedsMaximumAllowed() throws Exception {

        // maximum allowed: 10_000
        BetSlipDto betSlipDto = new BetSlipDto();
        betSlipDto.setEventId(1L);
        betSlipDto.setBetAmount(new BigDecimal("50.00"));
        betSlipDto.setBetOdds(new BigDecimal("1.67"));
        betSlipDto.setBetType(BetType.HOME_WIN);
        betSlipDto.setCouponCount(201);
        betSlipDto.setCurrencyCode("TL");

        BetSlipInitializeResponse expectedResponse = new BetSlipInitializeResponse();

        when(betSlipService.initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bet-slip/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betSlipDto))
                        .header("x-customer-id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());

        verify(betSlipService, never()).initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class));
    }

    @Test
    void initializeBetSlip_ShouldReturnBadRequest_WhenCouponCountExceedsMaximumAllowed() throws Exception {

        // maximum allowed: 10_000
        BetSlipDto betSlipDto = new BetSlipDto();
        betSlipDto.setEventId(1L);
        betSlipDto.setBetAmount(new BigDecimal("50.00"));
        betSlipDto.setBetOdds(new BigDecimal("1.67"));
        betSlipDto.setBetType(BetType.HOME_WIN);
        betSlipDto.setCouponCount(501);
        betSlipDto.setCurrencyCode("TL");

        BetSlipInitializeResponse expectedResponse = new BetSlipInitializeResponse();

        when(betSlipService.initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bet-slip/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betSlipDto))
                        .header("x-customer-id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());

        verify(betSlipService, never()).initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class));
    }

    @Test
    void initializeBetSlip_ShouldReturnBadRequest_WhenBetSlipDtoInvalid() throws Exception {

        BetSlipDto betSlipDto = new BetSlipDto();

        BetSlipInitializeResponse expectedResponse = new BetSlipInitializeResponse();

        when(betSlipService.initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bet-slip/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betSlipDto))
                        .header("x-customer-id", "1"))
                .andExpect(status().isBadRequest());

        verify(betSlipService, never()).initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class));

    }

    @Test
    void initializeBetSlip_ShouldReturnUnauthorized_WhenCustomerHeaderIsMissing() throws Exception {

        BetSlipDto betSlipDto = new BetSlipDto();

        BetSlipInitializeResponse expectedResponse = new BetSlipInitializeResponse();

        when(betSlipService.initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bet-slip/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betSlipDto)))
                .andExpect(status().isUnauthorized());

        verify(betSlipService, never()).initializeBetSlip(any(CustomerDto.class), any(BetSlipDto.class));

    }

    @Test
    void finalizeBetSlip_ShouldReturnResponseEntity() throws Exception {
        String inquiryId = "testInquiryId";
        BetSlipFinalizeResponse expectedResponse = new BetSlipFinalizeResponse(true);

        when(betSlipService.finalizeBetSlip(any(CustomerDto.class), eq(inquiryId)))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/api/bet-slip/finalize/{inquiryId}", inquiryId)
                        .header("x-customer-id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(betSlipService, atLeast(1)).finalizeBetSlip(any(CustomerDto.class), eq(inquiryId));
    }

    @Test
    void finalizeBetSlip_ShouldReturnBadRequest_WhenInquiryIdIsNull() throws Exception {
        mockMvc.perform(get("/api/bet-slip/finalize/"))
                .andExpect(status().isNotFound()); // Http 404
    }

    @Test
    void finalizeBetSlip_ShouldThrowException_WhenBetSlipExpired() throws Exception {
        String inquiryId = "testInquiryId";

        when(betSlipService.finalizeBetSlip(any(CustomerDto.class), eq(inquiryId)))
                .thenThrow(new BetSlipExpiredException("Bet slip expired"));

        mockMvc.perform(get("/api/bet-slip/finalize/{inquiryId}", inquiryId)
                        .header("x-customer-id", "1"))
                .andExpect(status().isInternalServerError());

        verify(betSlipService, atLeast(1)).finalizeBetSlip(any(CustomerDto.class), eq(inquiryId));
    }
}
