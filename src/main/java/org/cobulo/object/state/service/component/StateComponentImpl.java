package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.StateComponent;
import org.cobulo.object.state.service.controller.dto.ChangeStateDto;
import org.cobulo.object.state.service.controller.dto.StateHistoryInfoDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;
import org.cobulo.object.state.service.repository.StateHistoryRepository;
import org.cobulo.object.state.service.repository.StateTreeRepository;
import org.cobulo.object.state.service.repository.StatefulObjectRepository;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;


@Component
@Transactional
public class StateComponentImpl implements StateComponent {


    // Variables and constructors:-----------------------------------------------------------------
    private static final Logger LOGGER = getLogger(StateComponentImpl.class);

    private final StateTreeRepository stateTreeRepository;
    private final StateHistoryRepository stateHistoryRepository;
    private final StatefulObjectRepository statefulObjectRepository;

    @Autowired
    public StateComponentImpl(
            StateTreeRepository stateTreeRepository,
            StateHistoryRepository stateHistoryRepository,
            StatefulObjectRepository statefulObjectRepository
    ) {
        this.stateTreeRepository = stateTreeRepository;
        this.stateHistoryRepository = stateHistoryRepository;
        this.statefulObjectRepository = statefulObjectRepository;
    }


    // Public API:---------------------------------------------------------------------------------
    @Override
    public Optional<StateHistoryEntity> findActualHistoryRecord(
            final StateHistoryInfoDto stateHistoryInfo
    ) {
        LOGGER.debug("Requesting history records by {}", stateHistoryInfo);
        return stateHistoryRepository.findByOrderIdAndActualAndStatefulObject(
                requireNonNull(stateHistoryInfo.getOrderId(), "Requested order cannot be set as NULL"),
                true,
                readStateObject(stateHistoryInfo.getStatefulObject())
        );
    }

    @Override
    public Optional<StateTreeEntity> findBackwardState(
            final StateHistoryInfoDto stateHistoryInfo,
            final StateHistoryEntity stateHistory
    ) {
        LOGGER.debug("Requesting backward: {} {}", stateHistoryInfo, stateHistory);
        check(stateHistoryInfo, stateHistory);

        final StateTreeEntity stateTreeEntity = requireNonNull(
                stateHistory.getStateTree(), "State tree was removed #1");

        if (stateTreeEntity.isBackward()) {
            return ofNullable(stateTreeEntity.getParent());
        }
        return empty();
    }

    @Override
    public List<StateTreeEntity> findForwardState(
            final StateHistoryInfoDto stateHistoryInfo,
            final StateHistoryEntity stateHistory
    ) {
        LOGGER.debug("Requesting forward: {} {}", stateHistoryInfo, stateHistory);
        check(stateHistoryInfo, stateHistory);

        final StateTreeEntity stateTreeEntity = requireNonNull(
                stateHistory.getStateTree(), "State tree was removed #2");

        if (stateTreeEntity.isForward()) {
            return stateHistory.getStateTree().getChildList();
        }
        return emptyList();
    }

    @Override
    public StatefulObjectEntity findObject(final StatefulObjectDto statefulObject) {
        final StatefulObjectEntity statefulObjectEntity = readStateObject(statefulObject);
        LOGGER.trace("The stateful object was requested {} by {}", statefulObjectEntity, statefulObject);
        return readStateObject(statefulObject);
    }

    @Override
    public StateTreeEntity findTheFirstState(final StatefulObjectDto statefulObject) {

        final StatefulObjectEntity object = findObject(statefulObject);
        final List<StateTreeEntity> treeList = stateTreeRepository.findAllByStatefulObject(object);
        final StateTreeEntity treeEntity = treeList.stream()
                .filter(e -> isNull(e.getParent()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No trees were found by " + object));

        LOGGER.debug("The following tree parent was read: {}", treeEntity);
        return treeEntity;
    }

    @Override
    public StateTreeEntity findNewState(final ChangeStateDto changeState) {

        final String newStateCode = requireNonNull(changeState.getNewStateCode(),
                "New state code cannot be set as NULL");
        final StatefulObjectEntity statefulObject = findObject(
                changeState.getStateHistoryInfo().getStatefulObject());

        final StateTreeEntity stateTreeEntity = stateTreeRepository
                .findByStateCodeAndStatefulObject(newStateCode, statefulObject)
                .orElseThrow(() -> new IllegalArgumentException(
                        "State tree entity cannot be found: " + newStateCode + " and " + statefulObject));

        LOGGER.debug("The following state tree {} was found by {} and {}",
                stateTreeEntity, newStateCode, statefulObject);
        return stateTreeEntity;
    }


    // Internal methods:---------------------------------------------------------------------------
    private StatefulObjectEntity readStateObject(final StatefulObjectDto statefulObject) {
        return statefulObjectRepository.findBySystemIdAndSystemCode(
                requireNonNull(statefulObject.getSystemId(), "The system id cannot be set as NULL"),
                requireNonNull(statefulObject.getSystemCode(), "The system code cannot be set as NULL")
        ).orElseThrow(() ->
                new IllegalArgumentException("No stateful objects were found by the " + statefulObject));
    }

    private void check(
            final StateHistoryInfoDto stateHistoryInfo,
            final StateHistoryEntity stateHistory
    ) {
        if (isNull(stateHistory)) {
            throw new IllegalArgumentException("State history cannot be set as NULL");
        }
        if (!stateHistory.isActual()) {
            throw new IllegalArgumentException("State history is not actual: " + stateHistory);
        }
        if (!Objects.equals(stateHistoryInfo.getOrderId(), stateHistory.getOrderId())) {
            throw new IllegalStateException("Order IDs are not equal: " + stateHistoryInfo + " vs. " + stateHistory);
        }
    }
}
