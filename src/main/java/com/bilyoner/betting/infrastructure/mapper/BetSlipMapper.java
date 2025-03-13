package com.bilyoner.betting.infrastructure.mapper;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.domain.BetSlip;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BetSlipMapper extends EntityMapper<BetSlipDto, BetSlip> {
}
