package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryBatchDto;


public interface StateTreeDictionaryHolder {

    void insertNewDictionaries(StateDictionaryBatchDto stateTreeModel);

    StateDictionaryBatchDto readDictionaries();

}
