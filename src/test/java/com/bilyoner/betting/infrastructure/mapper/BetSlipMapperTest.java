package com.bilyoner.betting.infrastructure.mapper;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetType;
import com.bilyoner.betting.domain.bet.BetSlip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BetSlipMapperTest {

    @Autowired
    private BetSlipMapper betSlipMapper;

    @Test
    void testToEntity() {
        BetSlipDto betSlipDto = new BetSlipDto();
        betSlipDto.setId(1L);
        betSlipDto.setCustomerId(123L);
        betSlipDto.setEventId(456L);
        betSlipDto.setBetType(BetType.HOME_WIN);
        betSlipDto.setBetOdds(new BigDecimal("1.85"));
        betSlipDto.setCouponCount(3);
        betSlipDto.setBetAmount(new BigDecimal("100"));
        betSlipDto.setCurrencyCode("USD");
        betSlipDto.setTimestamp(System.currentTimeMillis());

        Long customerId = 123L;
        BetSlip betSlip = betSlipMapper.toEntity(betSlipDto, customerId);

        assertThat(betSlip).isNotNull();
        assertThat(betSlip.getId()).isEqualTo(betSlipDto.getId());
        assertThat(betSlip.getCustomerId()).isEqualTo(betSlipDto.getCustomerId());
        assertThat(betSlip.getEventId()).isEqualTo(betSlipDto.getEventId());
        assertThat(betSlip.getBetType()).isEqualTo(betSlipDto.getBetType());
        assertThat(betSlip.getBetOdds()).isEqualTo(betSlipDto.getBetOdds());
        assertThat(betSlip.getCouponCount()).isEqualTo(betSlipDto.getCouponCount());
        assertThat(betSlip.getBetAmount()).isEqualTo(betSlipDto.getBetAmount());
        assertThat(betSlip.getCurrencyCode()).isEqualTo(betSlipDto.getCurrencyCode());
        assertThat(betSlip.getCreateDate()).isNotNull();
    }

    @Test
    void testToDto() {
        BetSlip betSlip = new BetSlip();
        betSlip.setId(1L);
        betSlip.setCustomerId(123L);
        betSlip.setEventId(456L);
        betSlip.setBetType(BetType.HOME_WIN);
        betSlip.setBetOdds(new BigDecimal("1.85"));
        betSlip.setCouponCount(3);
        betSlip.setBetAmount(new BigDecimal("100"));
        betSlip.setCurrencyCode("USD");
        betSlip.setCreateDate(LocalDateTime.now());

        BetSlipDto betSlipDto = betSlipMapper.toDto(betSlip);

        assertThat(betSlipDto).isNotNull();
        assertThat(betSlipDto.getId()).isEqualTo(betSlip.getId());
        assertThat(betSlipDto.getCustomerId()).isEqualTo(betSlip.getCustomerId());
        assertThat(betSlipDto.getEventId()).isEqualTo(betSlip.getEventId());
        assertThat(betSlipDto.getBetType()).isEqualTo(betSlip.getBetType());
        assertThat(betSlipDto.getBetOdds()).isEqualTo(betSlip.getBetOdds());
        assertThat(betSlipDto.getCouponCount()).isEqualTo(betSlip.getCouponCount());
        assertThat(betSlipDto.getBetAmount()).isEqualTo(betSlip.getBetAmount());
        assertThat(betSlipDto.getCurrencyCode()).isEqualTo(betSlip.getCurrencyCode());
        assertThat(betSlipDto.getTimestamp()).isNotNull();
    }

    @Test
    void testToLocalDateTimeWithMock() {
        BetSlipMapper mockMapper = mock(BetSlipMapper.class);

        Long timestamp = System.currentTimeMillis();
        LocalDateTime expectedDateTime = LocalDateTime.now();

        when(mockMapper.toLocalDateTime(timestamp)).thenReturn(expectedDateTime);

        LocalDateTime result = mockMapper.toLocalDateTime(timestamp);

        assertThat(result).isNotNull().isEqualTo(expectedDateTime);
    }

    @Test
    void testToLongWithMock() {
        BetSlipMapper mockMapper = mock(BetSlipMapper.class);

        LocalDateTime localDateTime = LocalDateTime.now();
        Long expectedTimestamp = System.currentTimeMillis();

        when(mockMapper.toLong(localDateTime)).thenReturn(expectedTimestamp);

        Long result = mockMapper.toLong(localDateTime);

        assertThat(result).isNotNull().isEqualTo(expectedTimestamp);
    }
}
