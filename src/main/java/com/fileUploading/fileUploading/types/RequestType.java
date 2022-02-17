package com.fileUploading.fileUploading.types;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class RequestType {
    private String fileName;
    private String operType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
}
