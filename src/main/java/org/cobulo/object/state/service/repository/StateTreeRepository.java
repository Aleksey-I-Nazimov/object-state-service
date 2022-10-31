package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StateTreeEntity;
import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateTreeRepository extends CrudRepository<StateTreeEntity,Long> {

    List<StateTreeEntity> findAllByStatefulObject(StatefulObjectEntity statefulObject);

}
