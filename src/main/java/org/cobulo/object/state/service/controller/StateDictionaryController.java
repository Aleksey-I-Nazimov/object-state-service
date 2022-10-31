package org.cobulo.object.state.service.controller;

import org.cobulo.object.state.service.component.api.StateTreeDictionaryHolder;
import org.cobulo.object.state.service.controller.api.StateDictionaryApi;
import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryBatchDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;


@RestController
public class StateDictionaryController implements StateDictionaryApi {


    // Variables and constructors:-----------------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateDictionaryController.class);

    private final StateTreeDictionaryHolder stateTreeDictionaryHolder;

    @Autowired
    public StateDictionaryController(StateTreeDictionaryHolder stateTreeDictionaryHolder) {
        this.stateTreeDictionaryHolder = stateTreeDictionaryHolder;
    }


    // Public API:---------------------------------------------------------------------------------
    @Override
    public ResponseEntity<Void> insertDictionaryList(
            @Valid StateDictionaryBatchDto stateDictionaryBatchDto
    ) {
        LOGGER.info("The following dictionary batch was accepted for saving: {}", stateDictionaryBatchDto);
        stateTreeDictionaryHolder.insertNewDictionaries(stateDictionaryBatchDto);
        return ok(null);
    }

    @Override
    public ResponseEntity<StateDictionaryBatchDto> getDictionaries() {
        final StateDictionaryBatchDto batchDto = stateTreeDictionaryHolder.readDictionaries();
        LOGGER.info("The following dictionaries were read: {}", batchDto);
        return ok(batchDto);
    }
}
