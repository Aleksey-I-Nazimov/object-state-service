package org.cobulo.object.state.service.component.api;

import org.cobulo.object.state.service.controller.dto.basic.StateTreeModelDto;
import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;


public interface StateTreeModelHolder {

    void insertNewTreeModel(StateTreeModelDto stateTreeModel);

    StateTreeModelDto readTreeModel(StatefulObjectDto statefulObject);

}
