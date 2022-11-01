package org.cobulo.object.state.service.component.mapping;


import org.cobulo.object.state.service.controller.dto.basic.StateTreeDto;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static java.util.Optional.ofNullable;


@Mapper(componentModel = "spring")
public interface StateTreeModelMapping {


    @Mapping(target = "systemId", source = "systemId")
    @Mapping(target = "systemCode", source = "systemCode")
    @Mapping(target = "info", source = "info")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StatefulObjectEntity makeStatefulObject(StatefulObjectDto stateTreeModel);


    @Mapping(target = "systemId", source = "systemId")
    @Mapping(target = "systemCode", source = "systemCode")
    @Mapping(target = "info", source = "info")
    StatefulObjectDto makeStatefulObject(StatefulObjectEntity stateTreeModel);


    @Mapping(target = "statefulObject", source = "statefulObjectDto")
    @Mapping(target = "stateTree", source = "stateTreeDto")
    StateTreeModelDto makeStateTreeModel(StatefulObjectDto statefulObjectDto, StateTreeDto stateTreeDto);


    List<StateTreeEntity> makeStateTreeEntityList(List<StateTreeDto> stateTreeDtoList);


    @Mapping(target = "backward", source = "backward")
    @Mapping(target = "forward", source = "forward")
    @Mapping(target = "childList", source = "childList")
    @Mapping(target = "stateDictionary", source = "dictionaryCode")
    @Mapping(target = "stateCode", source = "stateCode")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "statefulObject", ignore = true)
    StateTreeEntity makeStateTreeEntity(StateTreeDto stateTreeDto);

    @AfterMapping
    default void substituteParents(@MappingTarget final StateTreeEntity stateTreeEntity) {
        ofNullable(stateTreeEntity.getChildList())
                .ifPresent(
                        list -> list.forEach(e -> e.setParent(stateTreeEntity))
                );
    }


    default StateDictionaryEntity makeStateDictionary(String dictionaryCode) {
        return ofNullable(dictionaryCode).map(code -> {
            final StateDictionaryEntity entity = new StateDictionaryEntity();
            entity.setCode(dictionaryCode);
            return entity;
        }).orElseThrow(() -> new NullPointerException("The dictionary code was set as null"));
    }


    List<StateTreeDto> makeStateTreeDtoList(List<StateTreeEntity> stateTreeDtoList);


    @Mapping(target = "backward", source = "backward")
    @Mapping(target = "forward", source = "forward")
    @Mapping(target = "childList", source = "childList")
    @Mapping(target = "dictionaryCode", source = "stateDictionary.code")
    @Mapping(target = "stateCode", source = "stateCode")
    StateTreeDto makeStateTreeDto(StateTreeEntity stateTreeDto);

}
