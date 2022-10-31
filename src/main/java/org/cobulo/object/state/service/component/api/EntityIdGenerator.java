package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;

public interface EntityIdGenerator {

    <T extends BasicEntity> T updateId(T basicEntity);

}
