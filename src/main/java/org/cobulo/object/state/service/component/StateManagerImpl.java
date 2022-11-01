package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.StateComponent;
import org.cobulo.object.state.service.component.api.StateManager;
import org.cobulo.object.state.service.component.api.StateRecorder;
import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.PossibleTreeStatesDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class StateManagerImpl implements StateManager {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateManagerImpl.class);

    private final StateComponent stateComponent;
    private final StateRecorder stateRecorder;

    @Autowired
    public StateManagerImpl(
            StateComponent stateComponent,
            StateRecorder stateRecorder
    ) {
        this.stateComponent = stateComponent;
        this.stateRecorder = stateRecorder;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    public PossibleTreeStatesDto findPossibleStates(
            final StateHistoryInfoDto stateTreeModel
    ) {
        LOGGER.debug("Finding possible trees states: {}", stateTreeModel);
        return findStates(stateTreeModel);
    }

    @Override
    public ChangeStateDto changeState(
            final ChangeStateDto changeStateDto
    ) {
        LOGGER.debug("Requesting state change: {}", changeStateDto);

        final PossibleTreeStatesDto possibleNewStates = findStates(changeStateDto.getStateHistoryInfo());

        if (isSuitable(changeStateDto, possibleNewStates)) {
            stateRecorder.makeRecord(changeStateDto);
            return changeStateDto;
        }

        throw new IllegalArgumentException("The change request " + changeStateDto
                + " cannot be applied with " + possibleNewStates);
    }


    // Internal methods:-----------------------------------------------------------------
    private PossibleTreeStatesDto findStates(
            final StateHistoryInfoDto stateTreeModel
    ) {
        final PossibleTreeStatesDto states = new PossibleTreeStatesDto();
        states.setStateHistoryInfo(stateTreeModel);

        return stateComponent.findActualHistoryRecord(stateTreeModel)
                .map(
                        actualHistoryRecord -> makeWithHistory(states, stateTreeModel, actualHistoryRecord)
                )
                .orElseGet(
                        () -> makeWithoutHistory(states, stateTreeModel)
                );
    }

    private PossibleTreeStatesDto makeWithHistory(
            final PossibleTreeStatesDto possibleTreeStates,
            final StateHistoryInfoDto stateHistoryInfo,
            final StateHistoryEntity stateHistory
    ) {

        // adding backward:----------------------
        stateComponent
                .findBackwardState(stateHistoryInfo, stateHistory)
                .ifPresent(state -> possibleTreeStates.setBackwardStateCode(state.getStateCode()));

        // adding forward:-----------------------
        stateComponent
                .findForwardState(stateHistoryInfo, stateHistory)
                .forEach(s -> possibleTreeStates.getForwardStateCodeList().add(s.getStateCode()));

        // adding current:-----------------------
        possibleTreeStates.getForwardStateCodeList().add(stateHistory.getStateTree().getStateCode());

        LOGGER.debug("Made states with history: {}", possibleTreeStates);
        return possibleTreeStates;
    }

    private PossibleTreeStatesDto makeWithoutHistory(
            final PossibleTreeStatesDto newStates,
            final StateHistoryInfoDto stateTreeModel
    ) {
        final String firstCode = stateComponent
                .findTheFirstState(stateTreeModel.getStatefulObject())
                .getStateCode();

        newStates.setBackwardStateCode(firstCode);
        newStates.getForwardStateCodeList().add(firstCode);

        LOGGER.debug("Made states without history: {}", newStates);
        return newStates;
    }

    private boolean isSuitable(
            final ChangeStateDto changeStateDto,
            final PossibleTreeStatesDto possibleNewStates
    ) {
        final String newStateCode = changeStateDto.getNewStateCode();

        final boolean flag = Objects.equals(possibleNewStates.getBackwardStateCode(), newStateCode) ||
                possibleNewStates.getForwardStateCodeList().contains(newStateCode);

        LOGGER.trace("Checking stateCode={} result={} possible: {}", newStateCode, flag, possibleNewStates);
        return flag;
    }
}
