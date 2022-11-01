package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.DictionaryComponent;
import org.cobulo.object.state.service.repository.dict.StateDictionaryRepository;
import org.cobulo.object.state.service.repository.dict.StateHistoryStatusRepository;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class DictionaryComponentImpl implements DictionaryComponent {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(DictionaryComponentImpl.class);

    private final StateDictionaryRepository stateDictionaryRepository;
    private final StateHistoryStatusRepository stateHistoryStatusRepository;

    public DictionaryComponentImpl(
            StateDictionaryRepository stateDictionaryRepository,
            StateHistoryStatusRepository stateHistoryStatusRepository
    ) {
        this.stateDictionaryRepository = stateDictionaryRepository;
        this.stateHistoryStatusRepository = stateHistoryStatusRepository;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    public StateDictionaryEntity findStateDictByCode(final String code) {

        final StateDictionaryEntity stateDictionary = stateDictionaryRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("No dictionaries by code = "
                        + code));

        LOGGER.trace("The state dictionary {} was read by {}", stateDictionary, code);
        return stateDictionary;
    }

    @Override
    public StateHistoryStatusEntity findStatusDictByCode(final String code) {

        final StateHistoryStatusEntity status = stateHistoryStatusRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Status cannot be read by code: " + code));

        LOGGER.trace("The status {} was read by {}", status, code);
        return status;
    }
}
