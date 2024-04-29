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

    @Column(name = "fileorfolder_id", nullable = false)
    private long fofId;
 
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "access_type", nullable = false)
    private EnumAccessType accessType;

    @Column(name = "grant_date", nullable = false)
    private Date grantDate;
    @Column(name="typefof",nullable = false)
    private int typefof;
    public AccessRightEntity() {
    }

    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public long getFolderId() {
        return fofId;
    }

    public void setFolderId(long fofId) {
        this.fofId = fofId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getTypefof(){
        return typefof;
    }
    public void setTypefof(int typefof){
        this.typefof=typefof;
    }
    public EnumAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(EnumAccessType accessType) {
        this.accessType = accessType;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }
    
    
}
