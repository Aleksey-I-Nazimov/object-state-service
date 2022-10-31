package org.cobulo.object.state.service.repository.dict;

import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StateDictionaryRepository extends CrudRepository<StateDictionaryEntity,Long> {

    Optional<StateDictionaryEntity> findByCode(String code);

}
