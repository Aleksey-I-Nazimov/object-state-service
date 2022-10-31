package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.ConcurrentStateRestrictionEntity;
import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcurrentStateRestrictionRepository extends CrudRepository<ConcurrentStateRestrictionEntity,Long> {
}
