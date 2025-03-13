package com.bilyoner.betting.infrastructure.mapper;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.Event;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper extends EntityMapper<EventDto, Event> {
}
