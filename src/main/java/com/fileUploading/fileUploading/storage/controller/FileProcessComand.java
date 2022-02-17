package com.fileUploading.fileUploading.storage.controller;

import com.fileUploading.fileUploading.storage.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class FileProcessComand {

    @Autowired
    private FileProcessCommandRepository fileSaveCommandRepository;
    private FileEntity entity;


    public FileProcessComand(FileProcessCommandRepository fileSaveCommandRepository, FileEntity entity) {
        this.fileSaveCommandRepository = fileSaveCommandRepository;
        this.entity = entity;
    }

    public void storeFile(){
        fileSaveCommandRepository.save(entity);
    }

    public FileEntity getFileDetail(String fileName){

        return fileSaveCommandRepository.getFileEntityByName(fileName);

    }


    public void deleteFile(String name){
        entity = this.getFileDetail(name);
        fileSaveCommandRepository.delete(entity);
    }

}
