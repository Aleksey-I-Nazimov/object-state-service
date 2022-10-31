package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.EntityIdGenerator;
import org.cobulo.object.state.service.component.api.StateTreeModelHolder;
import org.cobulo.object.state.service.component.mapping.StateTreeModelMapping;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeDto;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.cobulo.object.state.service.repository.StateTreeRepository;
import org.cobulo.object.state.service.repository.StatefulObjectRepository;
import org.cobulo.object.state.service.repository.dict.StateDictionaryRepository;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public class StateTreeModelHolderImpl implements StateTreeModelHolder {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateTreeModelHolderImpl.class);

    private final StateTreeModelMapping stateTreeModelMapping;
    private final StatefulObjectRepository statefulObjectRepository;
    private final StateDictionaryRepository stateDictionaryRepository;
    private final StateTreeRepository stateTreeRepository;
    private final EntityIdGenerator entityIdGenerator;

    @Autowired
    public StateTreeModelHolderImpl(
            StateTreeModelMapping stateTreeModelMapping,
            StatefulObjectRepository statefulObjectRepository,
            StateDictionaryRepository stateDictionaryRepository,
            StateTreeRepository stateTreeRepository,
            EntityIdGenerator entityIdGenerator
    ) {
        this.stateTreeModelMapping = stateTreeModelMapping;
        this.statefulObjectRepository = statefulObjectRepository;
        this.stateDictionaryRepository = stateDictionaryRepository;
        this.stateTreeRepository = stateTreeRepository;
        this.entityIdGenerator = entityIdGenerator;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    @Transactional
    public void insertNewTreeModel(final StateTreeModelDto stateTreeModel) {

        LOGGER.info("Accepted the new request of creating the tree model: {}", stateTreeModel);

        final StatefulObjectEntity statefulObject = updateAndSaveObject(stateTreeModel);
        updateAndSaveTree(stateTreeModel, statefulObject);
    }

    @Override
    @Transactional
    public StateTreeModelDto readTreeModel(final StatefulObjectDto statefulObject) {
        LOGGER.info("Accepted the new request of reading existed tree model: {}", statefulObject);

        final StatefulObjectEntity entity = statefulObjectRepository
                .findBySystemIdAndSystemCode(
                        statefulObject.getSystemId(),
                        statefulObject.getSystemCode()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException("No stateful object is available for " + statefulObject));

        final StateTreeDto stateTreeDto = makeFromEntityList(entity);
        final StatefulObjectDto statefulObjectDto = stateTreeModelMapping.makeStatefulObject(entity);
        final StateTreeModelDto stateTreeModelDto = stateTreeModelMapping
                .makeStateTreeModel(statefulObjectDto, stateTreeDto);

        LOGGER.debug("The following tree model was created: {}", stateTreeModelDto);
        return stateTreeModelDto;
    }


    // Internal private methods:---------------------------------------------------------
    private StatefulObjectEntity updateAndSaveObject(final StateTreeModelDto stateTreeModel) {
        final StatefulObjectEntity statefulObject = entityIdGenerator.updateId(
                stateTreeModelMapping.makeStatefulObject(stateTreeModel.getStatefulObject()));
        statefulObjectRepository.save(statefulObject);
        LOGGER.debug("The following stateful object was put to hibernate: {}", statefulObject);
        return statefulObject;
    }

    private void updateAndSaveTree(
            final StateTreeModelDto stateTreeModel,
            final StatefulObjectEntity statefulObject
    ) {
        final StateTreeEntity stateTree = updateStateEntityByDictionary(
                updateStateEntityByObject(
                        stateTreeModelMapping.makeStateTreeEntity(stateTreeModel.getStateTree()),
                        statefulObject
                )
        );

        stateTreeRepository.save(stateTree);
        LOGGER.debug("The following state tree was put to hibernate: {}", stateTree);
    }

    private StateTreeEntity updateStateEntityByObject(
            final StateTreeEntity stateTree,
            final StatefulObjectEntity statefulObject
    ) {
        final StateTreeEntity updatedStateTree = entityIdGenerator.updateId(stateTree);

        updatedStateTree.setStatefulObject(statefulObject);

        ofNullable(updatedStateTree.getChildList())
                .ifPresent(list -> list.forEach(child -> updateStateEntityByObject(child, statefulObject)));

        LOGGER.trace("Updated state tree entity by object: {}", stateTree);
        return updatedStateTree;
    }

    private StateTreeEntity updateStateEntityByDictionary(
            final StateTreeEntity stateTree
    ) {
        final String dictionaryCode = requireNonNull(stateTree.getStateDictionary().getCode(),
                "The dictionary code was skipped");

        final StateDictionaryEntity stateDictionary = stateDictionaryRepository.findByCode(dictionaryCode)
                .orElseThrow(() -> new IllegalArgumentException("No dictionaries by code = "
                        + dictionaryCode));

        stateTree.setStateDictionary(stateDictionary);

        ofNullable(stateTree.getChildList())
                .ifPresent(list -> list.forEach(this::updateStateEntityByDictionary)
                );

        LOGGER.trace("Updated state tree entity by dictionary: {}", stateTree);
        return stateTree;
    }

    private StateTreeDto makeFromEntityList(
            final StatefulObjectEntity entity
    ) {
        final List<StateTreeEntity> treeList = stateTreeRepository.findAllByStatefulObject(entity);
        final StateTreeEntity treeEntity = treeList.stream()
                .filter(e -> isNull(e.getParent()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No trees were found by " + entity));
        final StateTreeDto treeDto = stateTreeModelMapping.makeStateTreeDto(treeEntity);

        LOGGER.debug("The following tree parent was read: {}", treeDto);
        return treeDto;
    }

}
