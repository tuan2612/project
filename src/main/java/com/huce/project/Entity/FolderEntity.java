package com.huce.project.Entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "folders")
public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long folderId;

    @Column(name = "foldername", length = 255, nullable = false)
    private String foldername;

    @Column(name = "created_at")
    private Date createdAt;
    @Column(name="folder_size",nullable = false,length = 20)
    private long folder_size;
    public FolderEntity() {
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public long getFoldersize(){
        return folder_size;
    }
    public void setFoldersize(long folder_size){
        this.folder_size=folder_size;
    }
}
