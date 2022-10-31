package org.cobulo.object.state.service.controller.api;

import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface StateTreeModelApi {

    @PostMapping(
            value = "/insert-new-tree-model",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<Void> insertNewTreeModel(@RequestBody @Valid StateTreeModelDto stateTreeModel);

    @PostMapping(
            value = "/read-tree-model",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<StateTreeModelDto> readTreeModel(@RequestBody @Valid StatefulObjectDto statefulObject);

}
