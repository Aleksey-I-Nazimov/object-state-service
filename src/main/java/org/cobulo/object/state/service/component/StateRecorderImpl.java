package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.DictionaryComponent;
import org.cobulo.object.state.service.component.api.EntityIdGenerator;
import org.cobulo.object.state.service.component.api.StateComponent;
import org.cobulo.object.state.service.component.api.StateRecorder;
import org.cobulo.object.state.service.component.mapping.StateHistoryMapping;
import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.repository.StateHistoryRepository;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class StateRecorderImpl implements StateRecorder {


    // Variables and constructors:-----------------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateRecorderImpl.class);

    private final StateComponent stateComponent;
    private final EntityIdGenerator entityIdGenerator;
    private final DictionaryComponent dictionaryComponent;
    private final StateHistoryMapping stateHistoryMapping;
    private final StateHistoryRepository stateHistoryRepository;

    @Autowired
    public StateRecorderImpl(
            StateComponent stateComponent,
            EntityIdGenerator entityIdGenerator,
            DictionaryComponent dictionaryComponent,
            StateHistoryMapping stateHistoryMapping,
            StateHistoryRepository stateHistoryRepository
    ) {
        this.stateComponent = stateComponent;
        this.entityIdGenerator = entityIdGenerator;
        this.dictionaryComponent = dictionaryComponent;
        this.stateHistoryMapping = stateHistoryMapping;
        this.stateHistoryRepository = stateHistoryRepository;
    }

    // Public API:---------------------------------------------------------------------------------
    @Override
    public void makeRecord(final ChangeStateDto changeState) {

        final StateHistoryEntity stateHistory = make(changeState);
        stateHistoryRepository.save(stateHistory);
        LOGGER.trace("Making state history record completed!");
    }


    // Internal methods:---------------------------------------------------------------------------
    private StateHistoryEntity make(final ChangeStateDto changeState) {

        final StateHistoryEntity parentStateHistory = stateComponent
                .findActualHistoryRecord(changeState.getStateHistoryInfo())
                .orElse(null);
        final StateHistoryStatusEntity stateHistoryStatus = dictionaryComponent
                .findStatusDictByCode(changeState.getStatusCode());
        final StateTreeEntity stateTree = stateComponent
                .findNewState(changeState);
        final StatefulObjectEntity statefulObject = stateComponent
                .findObject(changeState.getStateHistoryInfo().getStatefulObject());

        final StateHistoryEntity newStateHistory = entityIdGenerator.updateId(
                stateHistoryMapping.make(changeState, parentStateHistory, stateHistoryStatus, stateTree, statefulObject));

        if (nonNull(parentStateHistory)) {
            parentStateHistory.setActual(false);
        }
        newStateHistory.setActual(true);

        LOGGER.debug("The new created state history: {}", newStateHistory);
        return newStateHistory;
    }


}
