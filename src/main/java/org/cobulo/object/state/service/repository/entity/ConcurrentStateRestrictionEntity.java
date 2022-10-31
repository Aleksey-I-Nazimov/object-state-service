package org.cobulo.object.state.service.repository.entity;


import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="concurrent_state_restriction")
public final class ConcurrentStateRestrictionEntity extends BasicEntity {

    @Column(name = "primary_state_id", unique = true)
    private long primaryStateId;

    @Column(name="slave_state_id")
    private long slave_state_id;

    @Column(name="enabled")
    private boolean enabled=true;

    @Column(name="info")
    private String info;


    public long getPrimaryStateId() {
        return primaryStateId;
    }

    public void setPrimaryStateId(long primaryStateId) {
        this.primaryStateId = primaryStateId;
    }

    public long getSlave_state_id() {
        return slave_state_id;
    }

    public void setSlave_state_id(long slave_state_id) {
        this.slave_state_id = slave_state_id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ConcurrentStateRestrictionEntity{" +
                "primaryStateId=" + primaryStateId +
                ", slave_state_id=" + slave_state_id +
                ", enabled=" + enabled +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
