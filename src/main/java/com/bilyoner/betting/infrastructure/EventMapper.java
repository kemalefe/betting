package com.bilyoner.betting.infrastructure;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.bet.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper extends EntityMapper<EventDto, Event> {

    @Mapping(source = "homeWinBetOdds", target = "betOddsDto.homeWinBetOdds")
    @Mapping(source = "drawBetOdds", target = "betOddsDto.drawBetOdds")
    @Mapping(source = "awayWinBetOdds", target = "betOddsDto.awayWinBetOdds")
    @Mapping(source = "id", target = "betOddsDto.eventId")
    @Mapping(target = "betOddsDto.updateTimestamp", expression = "java(0L)")
    EventDto toDto(Event event);


    @Mapping(source = "betOddsDto.homeWinBetOdds", target = "homeWinBetOdds")
    @Mapping(source = "betOddsDto.drawBetOdds", target = "drawBetOdds")
    @Mapping(source = "betOddsDto.awayWinBetOdds", target = "awayWinBetOdds")
    Event toEntity(EventDto eventDto);
}
