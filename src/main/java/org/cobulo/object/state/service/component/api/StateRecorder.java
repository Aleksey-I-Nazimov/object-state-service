package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.controller.dto.ChangeStateDto;


public interface StateRecorder {

    void makeRecord(ChangeStateDto changeState);

}
