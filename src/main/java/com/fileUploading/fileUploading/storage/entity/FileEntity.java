package com.fileUploading.fileUploading.storage.entity;

import javax.persistence.*;

@Entity
@Table
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fileId;
    private String name;
    private String path;
    private String type;
    private String size;


    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
