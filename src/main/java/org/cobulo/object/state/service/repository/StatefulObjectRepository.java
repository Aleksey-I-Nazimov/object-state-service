package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatefulObjectRepository extends CrudRepository<StatefulObjectEntity,Long> {
}
