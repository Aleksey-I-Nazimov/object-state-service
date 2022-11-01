package org.cobulo.object.state.service.controller.api;

import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.PossibleTreeStatesDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface StateOperationApi {

    @PostMapping(
            value = "/find-possible-states",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<PossibleTreeStatesDto> findPossibleStates(@RequestBody @Valid StateHistoryInfoDto stateHistoryInfo);

    @PostMapping(
            value = "/change-state",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<ChangeStateDto> changeState(@RequestBody @Valid ChangeStateDto changeState);

}
