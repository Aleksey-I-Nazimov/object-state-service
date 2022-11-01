package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;

import java.util.List;
import java.util.Optional;


public interface StateComponent {

    Optional<StateHistoryEntity> findActualHistoryRecord(
            StateHistoryInfoDto stateHistoryInfo
    );

    Optional<StateTreeEntity> findBackwardState(
            StateHistoryInfoDto stateHistoryInfo,
            StateHistoryEntity stateHistory
    );

    List<StateTreeEntity> findForwardState(
            StateHistoryInfoDto stateHistoryInfo,
            StateHistoryEntity stateHistory
    );

    StatefulObjectEntity findObject(StatefulObjectDto statefulObject);

    StateTreeEntity findTheFirstState(StatefulObjectDto statefulObject);

    StateTreeEntity findNewState(ChangeStateDto changeState);

}
