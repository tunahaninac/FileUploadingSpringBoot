package com.fileUploading.fileUploading.types;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class FileResponseType {

    private ResponseType responseType;
    private String name;
    private byte[] fileByteArray;
    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileByteArray() {
        return fileByteArray;
    }

    public void setFileByteArray(byte[] fileByteArray) {
        this.fileByteArray = fileByteArray;
    }


}
