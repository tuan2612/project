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
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    @Column(name = "filename", length = 255, nullable = false)
    private String filename;

    @Column(name = "filetype", length = 50, nullable = false)
    private String filetype;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "folder_id", nullable = false)
    private long folder_id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;




}
