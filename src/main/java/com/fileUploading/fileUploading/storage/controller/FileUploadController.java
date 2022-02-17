package com.fileUploading.fileUploading.storage.controller;

import com.fileUploading.fileUploading.storage.StorageFileNotFoundException;
import com.fileUploading.fileUploading.storage.entity.FileEntity;
import com.fileUploading.fileUploading.storage.service.StorageService;
import com.fileUploading.fileUploading.types.FileResponseType;
import com.fileUploading.fileUploading.types.RequestType;
import com.fileUploading.fileUploading.types.ResponseType;
import com.fileUploading.fileUploading.types.StorageProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;


@Controller
@Api(value = "File API")
public class FileUploadController {

    private final StorageService storageService;
    private final StorageProperties storageProperties;
    private final FileProcessCommandRepository fileCommandRepository;

    @Autowired
    public FileUploadController(StorageService storageService, StorageProperties storageProperties, FileProcessCommandRepository fileProcessCommandRepository) {
        this.storageService = storageService;
        this.storageProperties = storageProperties;
        this.fileCommandRepository = fileProcessCommandRepository;
    }

    @GetMapping("/fileDetail")
    public ResponseEntity<FileEntity> getFileDetails(@RequestBody RequestType requestType) {
        FileEntity entity = new FileEntity();
        if (requestType != null && requestType.getFileName() != null
                && !requestType.getFileName().isEmpty()) {
            FileProcessComand cmd = new FileProcessComand(this.fileCommandRepository, entity);
            entity = cmd.getFileDetail(requestType.getFileName());

        }
        return ResponseEntity.ok().body(entity);


    }


    @GetMapping("/updateFile")
    public ResponseEntity<ResponseType> updateFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName) {
        ResponseType responseType = new ResponseType();
        FileEntity entity = new FileEntity();
        if (file != null && fileName != null && !fileName.isEmpty()) {
            FileProcessComand cmd = new FileProcessComand(this.fileCommandRepository, entity);
            entity = cmd.getFileDetail(fileName);
            Path filePath = storageService.load(entity.getName());
            File updateFiles = new File(String.valueOf(filePath.toFile()));
            boolean isSaved = false;
            if (updateFiles.delete())
                isSaved = storageService.store(file);
            if (isSaved) {
				entity.setName(file.getName());
				entity.setType(file.getContentType());
				float size = (float) file.getSize() / (1024 * 1024);
				DecimalFormat df = new DecimalFormat("#.###");
				entity.setSize(df.format(size) + "MB");
                responseType.setRestunMsg("SCSS");
                responseType.setReturnCode("100");
                return ResponseEntity.ok().body(responseType);
            } else {

                responseType.setRestunMsg("ERR");
                responseType.setReturnCode("999");
                return ResponseEntity.badRequest().body(responseType);
            }


        } else {

            responseType.setRestunMsg("Missing Parameter");
            responseType.setReturnCode("120");
            return ResponseEntity.badRequest().body(responseType);


        }
    }


    @GetMapping("/deleteFile")
    public ResponseEntity<ResponseType> deleteFile(@RequestBody RequestType requestType) throws IOException {
        ResponseType responseType = new ResponseType();
        FileEntity processEntity = new FileEntity();
        if (requestType != null && requestType.getFileName() != null &&
                requestType.getOperType() != null && !requestType.getOperType().isEmpty()
                && !requestType.getOperType().isEmpty()) {
            Resource processfile = storageService.loadAsResource(requestType.getFileName());
            FileProcessComand cmd = new FileProcessComand(this.fileCommandRepository, processEntity);
            File file1 = new File(processfile.getFile().getAbsolutePath());
            if ("DEL".equals(requestType.getOperType())) {
                cmd.deleteFile(requestType.getFileName());
                file1.delete();
                responseType.setReturnCode("100");
                responseType.setRestunMsg("SCSS");
                return ResponseEntity.ok().body(responseType);

            } else {
                responseType.setRestunMsg("Wrong Oper");
                responseType.setReturnCode("99");
                return ResponseEntity.ok().body(responseType);
            }
        } else {
            responseType.setRestunMsg("Missing Parameter");
            responseType.setReturnCode("120");
            return ResponseEntity.ok().body(responseType);

        }


    }


    @GetMapping("/file")
    public ResponseEntity<FileResponseType> serveFile(@RequestBody RequestType requestType) throws IOException {
        FileResponseType fileResponseType = new FileResponseType();
        ResponseType respType = new ResponseType();
        if (requestType != null && requestType.getFileName() != null && !requestType.getFileName().isEmpty()) {
            Resource file = storageService.loadAsResource(requestType.getFileName());
            File file1 = new File(file.getFile().getAbsolutePath());
            respType.setReturnCode("100");
            respType.setRestunMsg("sccss");
            fileResponseType.setResponseType(respType);
            fileResponseType.setName(file1.getName());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            try {
                FileInputStream fis = new FileInputStream(file1);
                while ((read = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, read);
                }
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] fileByteArray = baos.toByteArray();
            fileResponseType.setFileByteArray(fileByteArray);
            return ResponseEntity.ok().body(fileResponseType);
        } else {
            respType.setRestunMsg("ERR");
            respType.setReturnCode("999");
            fileResponseType.setResponseType(respType);
            return ResponseEntity.badRequest().body(fileResponseType);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseType> handleFileUpload(@RequestParam("file") MultipartFile file) {
        ResponseType respType = new ResponseType();
        boolean isSave = false;
        isSave = storageService.store(file);
        if (isSave) {
            FileEntity entity = new FileEntity();
            entity.setName(file.getOriginalFilename());
            float size = (float) file.getSize() / (1024 * 1024);
            DecimalFormat df = new DecimalFormat("#.###");
			entity.setSize(df.format(size) + "MB");
            entity.setPath(storageProperties.getLocation());
            entity.setType(file.getContentType());
            FileProcessComand cmd = new FileProcessComand(this.fileCommandRepository, entity);
            cmd.storeFile();
            respType.setRestunMsg("SCSS");
            respType.setReturnCode("100");
        }
        return ResponseEntity.status(HttpStatus.OK).body(respType);

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
