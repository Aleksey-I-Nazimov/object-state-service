package org.cobulo.object.state.service.component.mapping;


import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryBatchDto;
import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryDto;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;


@Mapper(componentModel = "spring")
public interface StateDictionaryMapping {


    @Mapping(target = "code", source = "code")
    @Mapping(target = "info", source = "info")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StateDictionaryEntity makeFromDto(StateDictionaryDto stateDictionary);


    @Mapping(target = "code", source = "code")
    @Mapping(target = "info", source = "info")
    StateDictionaryDto makeFromEntity(StateDictionaryEntity stateDictionary);


    List<StateDictionaryEntity> makeFromDtoList(List<StateDictionaryDto> stateDictionaryList);


    List<StateDictionaryDto> makeFromEntityList(List<StateDictionaryEntity> stateDictionaryList);


    default List<StateDictionaryEntity> makeFromDtoList(final StateDictionaryBatchDto stateDictionaryBatchDto) {
        return ofNullable(stateDictionaryBatchDto).map(b -> makeFromDtoList(b.getDictionaryList())).orElse(emptyList());
    }

    default StateDictionaryBatchDto makeBatchFromEntityList(final List<StateDictionaryEntity> entities) {
        return ofNullable(entities).map(
                list -> {
                    final StateDictionaryBatchDto batch = new StateDictionaryBatchDto();
                    batch.setDictionaryList(makeFromEntityList(list));
                    return batch;
                }
        )
                .orElse(new StateDictionaryBatchDto());
    }


}
