package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "COMISSION", indexes = {
        @Index(name = "IDX_COMISSION_HS_MANAGER", columnList = "HS_MANAGER_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_COMISSION_UNQ_NAME", columnNames = {"NAME"})
})
@Entity
public class Comission {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "HS_MANAGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User hsManager;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public User getHsManager() {
        return hsManager;
    }

    public void setHsManager(User hsManager) {
        this.hsManager = hsManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}