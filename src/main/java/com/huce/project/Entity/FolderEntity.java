package com.huce.project.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "folder_size", nullable = false, length = 20)
    private long folder_size;

    public FolderEntity() {
    }

    public FolderEntity(long folderId, String foldername, Date createdAt, long folder_size) {
        this.folderId = folderId;
        this.foldername = foldername;
        this.createdAt = createdAt;
        this.folder_size = folder_size;
    }

    // Getter and Setter for folderId
    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    // Getter and Setter for foldername
    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    // Getter and Setter for createdAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Getter and Setter for folder_size
    public long getFolder_size() {
        return folder_size;
    }

    public void setFolder_size(long folder_size) {
        this.folder_size = folder_size;
    }
}
