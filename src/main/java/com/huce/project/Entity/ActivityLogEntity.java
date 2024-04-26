package com.huce.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Activity_Log")
public class ActivityLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userID;

    @Column(name = "activity")
    private String activity;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity fileID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUserID() {
        return userID;
    }

    public void setUserID(UserEntity userID) {
        this.userID = userID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public FileEntity getFileID() {
        return fileID;
    }

    public void setFileID(FileEntity fileID) {
        this.fileID = fileID;
    }

    public ActivityLogEntity() {
    }

}
