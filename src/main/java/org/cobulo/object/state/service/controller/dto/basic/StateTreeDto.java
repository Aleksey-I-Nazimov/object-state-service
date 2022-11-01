package org.cobulo.object.state.service.controller.dto.basic;

import java.util.List;

public final class StateTreeDto {

    private boolean backward;
    private boolean forward;
    private String stateCode;
    private String dictionaryCode;
    private List<StateTreeDto> childList;

    public boolean isBackward() {
        return backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }


    public List<StateTreeDto> getChildList() {
        return childList;
    }

    public void setChildList(List<StateTreeDto> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "StateTreeDto{" +
                "backward=" + backward +
                ", forward=" + forward +
                ", stateCode='" + stateCode + '\'' +
                ", dictionaryCode='" + dictionaryCode + '\'' +
                ", childList=" + childList +
                '}';
    }
}
