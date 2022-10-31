package org.cobulo.object.state.service.repository;

import org.cobulo.object.state.service.repository.entity.StatefulObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StatefulObjectRepository extends CrudRepository<StatefulObjectEntity,Long> {

    Optional<StatefulObjectEntity> findBySystemIdAndSystemCode(String systemId, String systemCode);

}
