package com.huce.project.entity;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "folders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long folderId;

    @Column(name = "foldername", length = 255, nullable = false)
    private String foldername;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name="folder_size",nullable = false,length = 20)
    private long folder_size;

}
