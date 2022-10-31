package org.cobulo.object.state.service.repository.entity.dict;


import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The state dictionary table. This table contains text descriptors of different object tree state
 *
 * @author Nazimov Aleksey I.
 */
@Entity
@Table(name="state_dictionary")
public final class StateDictionaryEntity extends BasicEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name="info",nullable = false)
    private String info;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StateDictionaryEntity{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
