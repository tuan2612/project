package com.huce.project.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Access_Rights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessRightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accessId;

    @Column(name = "fileorfolder_id", nullable = false)
    private long fofId;
 
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "access_type", nullable = false)
    private EnumAccessType accessType;

    @Column(name="typefof",nullable = false)
    private int typefof;
    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }

    public long getFofId() {
        return fofId;
    }

    public void setFofId(long fofId) {
        this.fofId = fofId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public EnumAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(EnumAccessType accessType) {
        this.accessType = accessType;
    }

    public int getTypefof() {
        return typefof;
    }

    public void setTypefof(int typefof) {
        this.typefof = typefof;
    }
}