package com.bilyoner.betting.infrastructure;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.domain.bet.BetSlip;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BetSlipMapper extends EntityMapper<BetSlipDto, BetSlip> {

    @Mapping(target = ".", source = "betSlipDto")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "createDate", source = "betSlipDto.timestamp")
    BetSlip toEntity(BetSlipDto betSlipDto, Long customerId);

    @Override
    @Mapping(target = "timestamp", source = "createDate")
    BetSlipDto toDto(BetSlip betSlip);

    default LocalDateTime toLocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    default Long toLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
