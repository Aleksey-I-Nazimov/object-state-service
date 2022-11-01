package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;

public interface DictionaryComponent {

    StateDictionaryEntity findStateDictByCode(String code);

    StateHistoryStatusEntity findStatusDictByCode(String code);

}
