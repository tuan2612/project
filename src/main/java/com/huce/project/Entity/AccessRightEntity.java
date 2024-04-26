package com.huce.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "Access_Rights")
public class AccessRightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity fileId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @Column(name = "access_type", nullable = false)
    private EnumAccessType accessType;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleId;

    @Column(name = "grant_date", nullable = false)
    private Date grantDate;

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public FileEntity getFileId() {
        return fileId;
    }

    public void setFileId(FileEntity fileId) {
        this.fileId = fileId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public EnumAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(EnumAccessType accessType) {
        this.accessType = accessType;
    }

    public RoleEntity getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleEntity roleId) {
        this.roleId = roleId;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public AccessRightEntity() {
        this.roleId = new RoleEntity();
        this.roleId.setRoleID(1);
    }
    
}
