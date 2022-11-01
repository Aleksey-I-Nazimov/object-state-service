package org.cobulo.object.state.service.component.mapping;


import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface StateHistoryMapping {


    @Mapping(target = "orderId", source = "changeState.stateHistoryInfo.orderId")
    @Mapping(target = "actual", constant = "true")
    @Mapping(target = "timestamp", source = "changeState.timestamp")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "stateHistoryStatus", source = "status")
    @Mapping(target = "stateTree", source = "stateTree")
    @Mapping(target = "statefulObject", source = "statefulObject")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StateHistoryEntity make(
            ChangeStateDto changeState,
            StateHistoryEntity parent,
            StateHistoryStatusEntity status,
            StateTreeEntity stateTree,
            StatefulObjectEntity statefulObject
    );

}
