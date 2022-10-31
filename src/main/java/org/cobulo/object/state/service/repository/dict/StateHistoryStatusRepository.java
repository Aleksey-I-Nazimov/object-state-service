package org.cobulo.object.state.service.repository.dict;

import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StateHistoryStatusRepository extends CrudRepository<StateHistoryStatusEntity,Long> {

}
