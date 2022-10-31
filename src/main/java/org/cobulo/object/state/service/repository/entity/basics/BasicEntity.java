package org.cobulo.object.state.service.repository.entity.basics;


import javax.persistence.*;

import java.time.Instant;
import java.util.Objects;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;


@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class BasicEntity {

    @Id
    protected long id;

    @Column(name="created_at",nullable = false)
    protected Instant createdAt;

    @Column(name="updated_at")
    protected Instant updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BasicEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicEntity)) return false;
        BasicEntity that = (BasicEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
