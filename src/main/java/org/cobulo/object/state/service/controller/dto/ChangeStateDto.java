package org.cobulo.object.state.service.controller.dto;

public final class ChangeStateDto {

    private StateHistoryInfoDto stateHistoryInfo;
    private String newStateCode;
    private String statusCode;
    private long timestamp;

    public StateHistoryInfoDto getStateHistoryInfo() {
        return stateHistoryInfo;
    }

    public void setStateHistoryInfo(StateHistoryInfoDto stateHistoryInfo) {
        this.stateHistoryInfo = stateHistoryInfo;
    }

    public String getNewStateCode() {
        return newStateCode;
    }

    public void setNewStateCode(String newStateCode) {
        this.newStateCode = newStateCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChangeStateDto{" +
                "stateHistoryInfo=" + stateHistoryInfo +
                ", newStateCode='" + newStateCode + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
