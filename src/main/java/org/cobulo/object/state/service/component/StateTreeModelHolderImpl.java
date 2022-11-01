package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.DictionaryComponent;
import org.cobulo.object.state.service.component.api.EntityIdGenerator;
import org.cobulo.object.state.service.component.api.StateComponent;
import org.cobulo.object.state.service.component.api.StateTreeModelHolder;
import org.cobulo.object.state.service.component.mapping.StateTreeModelMapping;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeDto;
import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.cobulo.object.state.service.repository.StateTreeRepository;
import org.cobulo.object.state.service.repository.StatefulObjectRepository;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class StateTreeModelHolderImpl implements StateTreeModelHolder {


    // Variables and constructors:-------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateTreeModelHolderImpl.class);

    private final StateComponent stateComponent;
    private final DictionaryComponent dictionaryComponent;
    private final StateTreeModelMapping stateTreeModelMapping;
    private final StatefulObjectRepository statefulObjectRepository;
    private final StateTreeRepository stateTreeRepository;
    private final EntityIdGenerator entityIdGenerator;

    @Autowired
    public StateTreeModelHolderImpl(
            StateComponent stateComponent,
            DictionaryComponent dictionaryComponent,
            StateTreeModelMapping stateTreeModelMapping,
            StatefulObjectRepository statefulObjectRepository,
            StateTreeRepository stateTreeRepository,
            EntityIdGenerator entityIdGenerator
    ) {
        this.stateComponent = stateComponent;
        this.dictionaryComponent = dictionaryComponent;
        this.stateTreeModelMapping = stateTreeModelMapping;
        this.statefulObjectRepository = statefulObjectRepository;
        this.stateTreeRepository = stateTreeRepository;
        this.entityIdGenerator = entityIdGenerator;
    }


    // Public API:-----------------------------------------------------------------------
    @Override
    public void insertNewTreeModel(
            final StateTreeModelDto stateTreeModel
    ) {
        LOGGER.info("Accepted the new request of creating the tree model: {}", stateTreeModel);

        final StatefulObjectEntity statefulObject = updateAndSaveObject(stateTreeModel);
        updateAndSaveTree(stateTreeModel, statefulObject);
    }

    @Override
    public StateTreeModelDto readTreeModel(
            final StatefulObjectDto statefulObject
    ) {
        LOGGER.info("Accepted the new request of reading existed tree model: {}", statefulObject);

        final StateTreeEntity stateTree = stateComponent.findTheFirstState(statefulObject);
        final StatefulObjectDto statefulObjectDto = stateTreeModelMapping
                .makeStatefulObject(stateTree.getStatefulObject());
        final StateTreeDto stateTreeDto = stateTreeModelMapping.makeStateTreeDto(stateTree);

        final StateTreeModelDto stateTreeModelDto = stateTreeModelMapping
                .makeStateTreeModel(statefulObjectDto, stateTreeDto);

        LOGGER.debug("The following tree model was created: {}", stateTreeModelDto);
        return stateTreeModelDto;
    }


    // Internal private methods:---------------------------------------------------------
    private StatefulObjectEntity updateAndSaveObject(
            final StateTreeModelDto stateTreeModel
    ) {
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

        stateTree.setStateDictionary(dictionaryComponent.findStateDictByCode(dictionaryCode));

        ofNullable(stateTree.getChildList())
                .ifPresent(list -> list.forEach(this::updateStateEntityByDictionary)
                );

        LOGGER.trace("Updated state tree entity by dictionary: {}", stateTree);
        return stateTree;
    }


}
