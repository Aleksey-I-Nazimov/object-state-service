package org.cobulo.object.state.service.controller;

import org.cobulo.object.state.service.component.api.StateManager;
import org.cobulo.object.state.service.controller.api.StateOperationApi;
import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.PossibleTreeStatesDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;


@RestController
public class StateOperationController implements StateOperationApi {

    private static final Logger LOGGER = getLogger(StateOperationController.class);

    private final StateManager stateManager;

    @Autowired
    public StateOperationController(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public ResponseEntity<PossibleTreeStatesDto> findPossibleStates(
            final @Valid StateHistoryInfoDto stateHistoryInfo
    ) {
        LOGGER.info("Finding possible states request: {}", stateHistoryInfo);
        final PossibleTreeStatesDto possibleTreeStates = stateManager.findPossibleStates(stateHistoryInfo);
        LOGGER.debug("Found possible states: {}", possibleTreeStates);
        return ok(possibleTreeStates);
    }

    @Override
    public ResponseEntity<ChangeStateDto> changeState(
            final @Valid ChangeStateDto changeState
    ) {
        LOGGER.info("Changing state request: {}", changeState);
        final ChangeStateDto newState = stateManager.changeState(changeState);
        return ok(newState);
    }
}
