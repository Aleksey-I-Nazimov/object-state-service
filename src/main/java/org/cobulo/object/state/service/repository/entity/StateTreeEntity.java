package org.cobulo.object.state.service.repository.entity;


import org.cobulo.object.state.service.repository.entity.basics.BasicEntity;
import org.cobulo.object.state.service.repository.entity.dict.StateDictionaryEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="state_tree")
public final class StateTreeEntity extends BasicEntity {

    /*
    The columnst parent_id, dictionary_id and object_id are generated automatically
     */
    @Column(name="backward")
    private boolean backward;

    @Column(name="forward")
    private boolean forward;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private StateTreeEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<StateTreeEntity> childList;

    @ManyToOne
    @JoinColumn(name="dictionary_id")
    private StateDictionaryEntity stateDictionary;

    @ManyToOne
    @JoinColumn(name="object_id")
    private StatefulObjectEntity statefulObject;



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

    public StateTreeEntity getParent() {
        return parent;
    }

    public void setParent(StateTreeEntity parent) {
        this.parent = parent;
    }

    public List<StateTreeEntity> getChildList() {
        return childList;
    }

    public void setChildList(List<StateTreeEntity> childList) {
        this.childList = childList;
    }

    public StateDictionaryEntity getStateDictionary() {
        return stateDictionary;
    }

    public void setStateDictionary(StateDictionaryEntity stateDictionary) {
        this.stateDictionary = stateDictionary;
    }

    public StatefulObjectEntity getStatefulObject() {
        return statefulObject;
    }

    public void setStatefulObject(StatefulObjectEntity statefulObject) {
        this.statefulObject = statefulObject;
    }

    @Override
    public String toString() {
        // Parent entity must be excluded
        return "StateTreeEntity{" +
                " backward=" + backward +
                ", forward=" + forward +
                ", childList=" + childList +
                ", stateDictionary=" + stateDictionary +
                ", statefulObject=" + statefulObject +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
