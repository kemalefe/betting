package com.bilyoner.betting.infrastructure.mapper;

import com.bilyoner.betting.contract.BetDto;
import com.bilyoner.betting.domain.Bet;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BetMapper extends EntityMapper<BetDto, Bet> {
}
