package org.cobulo.object.state.service.controller.dto;

import java.util.ArrayList;
import java.util.List;


public final class PossibleTreeStatesDto {

    private StateHistoryInfoDto stateHistoryInfo;
    private String backwardStateCode;
    private List<String> forwardStateCodeList = new ArrayList<>();

    public StateHistoryInfoDto getStateHistoryInfo() {
        return stateHistoryInfo;
    }

    public void setStateHistoryInfo(StateHistoryInfoDto stateHistoryInfo) {
        this.stateHistoryInfo = stateHistoryInfo;
    }

    public String getBackwardStateCode() {
        return backwardStateCode;
    }

    public void setBackwardStateCode(String backwardStateCode) {
        this.backwardStateCode = backwardStateCode;
    }

    public List<String> getForwardStateCodeList() {
        return forwardStateCodeList;
    }

    public void setForwardStateCodeList(List<String> forwardStateCodeList) {
        this.forwardStateCodeList = forwardStateCodeList;
    }

    @Override
    public String toString() {
        return "PossibleTreeStatesDto{" +
                "stateHistoryInfo=" + stateHistoryInfo +
                ", backwardStateCode='" + backwardStateCode + '\'' +
                ", forwardStateCodeList=" + forwardStateCodeList +
                '}';
    }

}
