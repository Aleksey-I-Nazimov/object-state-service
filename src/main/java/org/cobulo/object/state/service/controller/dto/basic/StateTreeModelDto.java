package org.cobulo.object.state.service.controller.dto.basic;


public final class StateTreeModelDto {

    private StateTreeDto stateTree;
    private StatefulObjectDto statefulObject;

    public StateTreeDto getStateTree() {
        return stateTree;
    }

    public void setStateTree(StateTreeDto stateTree) {
        this.stateTree = stateTree;
    }

    public StatefulObjectDto getStatefulObject() {
        return statefulObject;
    }

    public void setStatefulObject(StatefulObjectDto statefulObject) {
        this.statefulObject = statefulObject;
    }

    @Override
    public String toString() {
        return "StateTreeModelDto{" +
                "stateTree=" + stateTree +
                ", statefulObject=" + statefulObject +
                '}';
    }
}
