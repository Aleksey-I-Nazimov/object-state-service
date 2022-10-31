package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateTreeRepository extends CrudRepository<StateTreeEntity,Long> {
}
