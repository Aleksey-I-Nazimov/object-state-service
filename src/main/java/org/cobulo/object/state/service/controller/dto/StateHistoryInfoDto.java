package org.cobulo.object.state.service.controller.dto;

import org.cobulo.object.state.service.controller.dto.basic.StatefulObjectDto;


public final class StateHistoryInfoDto {

    private StatefulObjectDto statefulObject;
    private String orderId;

    public StatefulObjectDto getStatefulObject() {
        return statefulObject;
    }

    public void setStatefulObject(StatefulObjectDto statefulObject) {
        this.statefulObject = statefulObject;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "StateHistoryInfoDto{" +
                "statefulObject=" + statefulObject +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
