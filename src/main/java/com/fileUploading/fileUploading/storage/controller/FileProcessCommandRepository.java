package com.fileUploading.fileUploading.storage.controller;

import com.fileUploading.fileUploading.storage.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileProcessCommandRepository extends JpaRepository<FileEntity,Integer> {

    FileEntity getFileEntityByName(String name);



}
