package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StateHistoryEntity;
import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateHistoryRepository extends CrudRepository<StateHistoryEntity,Long> {
}
