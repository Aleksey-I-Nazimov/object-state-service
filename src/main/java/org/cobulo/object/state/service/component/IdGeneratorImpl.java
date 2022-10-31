package org.cobulo.object.state.service.component;

import org.cobulo.object.state.service.component.api.IdGenerator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;


@Component
public final class IdGeneratorImpl implements IdGenerator {

    private static final Logger LOGGER = getLogger(IdGeneratorImpl.class);

    @Override
    public long makeNumericalId() {
        final long id = randomUUID().getLeastSignificantBits();
        LOGGER.trace("The following id was requested: {}", id);
        return id;
    }
}
