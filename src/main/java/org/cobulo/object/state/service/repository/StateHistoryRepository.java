package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateHistoryRepository extends CrudRepository<StateHistoryEntity,Long> {

    Optional<StateHistoryEntity> findByOrderIdAndActualAndStatefulObject(
            String orderId, boolean actual, StatefulObjectEntity statefulObject);

}
