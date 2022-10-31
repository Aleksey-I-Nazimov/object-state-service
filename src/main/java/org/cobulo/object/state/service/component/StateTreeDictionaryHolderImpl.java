package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.EntityIdGenerator;
import org.cobulo.object.state.service.component.api.StateTreeDictionaryHolder;
import org.cobulo.object.state.service.component.mapping.StateDictionaryMapping;
import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryBatchDto;
import org.cobulo.object.state.service.repository.dict.StateDictionaryRepository;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class StateTreeDictionaryHolderImpl implements StateTreeDictionaryHolder {


    // Variables and constructors:-----------------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateTreeDictionaryHolderImpl.class);

    private final EntityIdGenerator entityIdGenerator;
    private final StateDictionaryMapping stateDictionaryMapping;
    private final StateDictionaryRepository stateDictionaryRepository;

    @Autowired
    public StateTreeDictionaryHolderImpl(
            EntityIdGenerator entityIdGenerator,
            StateDictionaryMapping stateDictionaryMapping,
            StateDictionaryRepository stateDictionaryRepository
    ) {
        this.entityIdGenerator = entityIdGenerator;
        this.stateDictionaryMapping = stateDictionaryMapping;
        this.stateDictionaryRepository = stateDictionaryRepository;
    }


    // Public API:---------------------------------------------------------------------------------
    @Override
    public void insertNewDictionaries(final StateDictionaryBatchDto stateTreeModel) {
        LOGGER.debug("Dictionary batch for saving: {} ", stateTreeModel);

        final List<StateDictionaryEntity> entities = stateDictionaryMapping
                .makeFromDtoList(stateTreeModel)
                .stream()
                .map(entityIdGenerator::updateId)
                .collect(toList());

        LOGGER.trace("Prepared dictionaries for saving: {}", entities);
        stateDictionaryRepository.saveAll(entities);
    }

    @Override
    public StateDictionaryBatchDto readDictionaries() {
        final List<StateDictionaryEntity> entities = new ArrayList<>();
        stateDictionaryRepository
                .findAll()
                .forEach(entities::add);

        final StateDictionaryBatchDto batchDto = stateDictionaryMapping.makeBatchFromEntityList(entities);
        LOGGER.debug("Read state dictionary batch: {}", batchDto);
        return batchDto;
    }
}
