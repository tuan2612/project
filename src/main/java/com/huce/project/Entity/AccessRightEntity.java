package com.huce.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "Access_Rights")
public class AccessRightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accessId;

    @Column(name = "file_id", nullable = false)
    private long fileId;

 
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "access_type", nullable = false)
    private EnumAccessType accessType;


    @Column(name = "role_id")
    private long roleId;

    @Column(name = "grant_date", nullable = false)
    private Date grantDate;

    public AccessRightEntity() {
    }

    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public EnumAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(EnumAccessType accessType) {
        this.accessType = accessType;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }
    
    
}
