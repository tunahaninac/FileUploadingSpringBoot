package com.fileUploading.fileUploading.types;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class ResponseType {
    private String ReturnCode;
    private String restunMsg;

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String returnCode) {
        ReturnCode = returnCode;
    }

    public String getRestunMsg() {
        return restunMsg;
    }

    public void setRestunMsg(String restunMsg) {
        this.restunMsg = restunMsg;
    }
}
