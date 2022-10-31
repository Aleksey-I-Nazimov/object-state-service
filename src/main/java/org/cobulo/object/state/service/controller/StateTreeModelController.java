package org.cobulo.object.state.service.controller;


import org.cobulo.object.state.service.component.api.StateTreeModelHolder;
import org.cobulo.object.state.service.controller.api.StateTreeModelApi;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;


@RestController
public class StateTreeModelController implements StateTreeModelApi {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateTreeModelController.class);

    private final StateTreeModelHolder stateTreeModelHolder;

    @Autowired
    public StateTreeModelController(StateTreeModelHolder stateTreeModelHolder) {
        this.stateTreeModelHolder = stateTreeModelHolder;
    }


    // Public API:------------------------------------------------------------------------
    @Override
    public ResponseEntity<Void> insertNewTreeModel(
            final @Valid StateTreeModelDto stateTreeModel
    ) {
        LOGGER.info("Controller received inserting request: {}", stateTreeModel);
        stateTreeModelHolder.insertNewTreeModel(stateTreeModel);
        return ok(null);
    }

    @Override
    public ResponseEntity<StateTreeModelDto> readTreeModel(
            final @Valid StatefulObjectDto statefulObject
    ) {
        LOGGER.info("Controller received reading state tree request: {}", statefulObject);
        final StateTreeModelDto treeModel = stateTreeModelHolder.readTreeModel(statefulObject);
        return ok(treeModel);
    }

}
