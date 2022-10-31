package org.cobulo.object.state.service.repository.entity;


import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The table of different objects associated with state schemas of analyzed systems
 *
 * @author Nazimov Aleksey I.
 */
@Entity
@Table(name="stateful_object")
public final class StatefulObjectEntity extends BasicEntity {

    @Column(name="system_id",nullable = false)
    private String systemId;

    @Column(name="system_code",nullable =false)
    private String systemCode;

    @Column(name="info",nullable = false)
    private String info;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StatefulObjectEntity{" +
                "systemId='" + systemId + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
