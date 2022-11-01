package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.PossibleTreeStatesDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;


public interface StateManager {

    PossibleTreeStatesDto findPossibleStates(StateHistoryInfoDto stateHistoryInfo);

    ChangeStateDto changeState(ChangeStateDto changeState);

}
