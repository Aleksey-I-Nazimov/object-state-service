package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.EntityIdGenerator;
import org.cobulo.object.state.service.component.api.IdGenerator;
import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class EntityIdGeneratorImpl implements EntityIdGenerator {

    private static final Logger LOGGER = getLogger(EntityIdGeneratorImpl.class);

    private final IdGenerator idGenerator;

    @Autowired
    public EntityIdGeneratorImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public <T extends BasicEntity> T updateId(final T basicEntity) {
        final long newId = idGenerator.makeNumericalId();
        basicEntity.setId(newId);
        LOGGER.trace("The following entity was updated by ID={}: {}", newId, basicEntity);
        return basicEntity;
    }
}
