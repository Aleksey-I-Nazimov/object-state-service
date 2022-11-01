package org.cobulo.object.state.service.repository.entity;


import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateHistoryStatusEntity;

import javax.persistence.*;


@Entity
@Table(name="state_history")
public final class StateHistoryEntity extends BasicEntity {

    /*
    The columns current_state_id, parent_id, status_id, object_id are generated automatically
     */
    @Column(name="order_id",nullable = false)
    private String orderId;

    @Column(name="actual")
    private boolean actual;

    @Column(name="timestamp")
    private long timestamp;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    private StatefulObjectEntity statefulObject;

    @ManyToOne
    @JoinColumn(name = "current_state_id", nullable = false)
    private StateTreeEntity stateTree;

    @OneToOne
    @JoinColumn(name="parent_id")
    private StateHistoryEntity parent;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StateHistoryStatusEntity stateHistoryStatus;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public StatefulObjectEntity getStatefulObject() {
        return statefulObject;
    }

    public void setStatefulObject(StatefulObjectEntity statefulObject) {
        this.statefulObject = statefulObject;
    }

    public StateTreeEntity getStateTree() {
        return stateTree;
    }

    public void setStateTree(StateTreeEntity stateTree) {
        this.stateTree = stateTree;
    }

    public StateHistoryEntity getParent() {
        return parent;
    }

    public void setParent(StateHistoryEntity parent) {
        this.parent = parent;
    }

    public StateHistoryStatusEntity getStateHistoryStatus() {
        return stateHistoryStatus;
    }

    public void setStateHistoryStatus(StateHistoryStatusEntity stateHistoryStatus) {
        this.stateHistoryStatus = stateHistoryStatus;
    }

    @Override
    public String toString() {
        return "StateHistoryEntity{" +
                "orderId='" + orderId + '\'' +
                ", actual=" + actual +
                ", timestamp=" + timestamp +
                ", statefulObject=" + statefulObject +
                ", stateTree=" + stateTree +
                ", parent=" + parent +
                ", stateHistoryStatus=" + stateHistoryStatus +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
