package com.chenyi.yanhuohui.common.base.exception;

import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SbcRuntimeException extends RuntimeException{
    private String errorCode = "";
    private Object data;
    private String result = "fail";
    private Object[] params;

    public SbcRuntimeException(){
        super();
        this.errorCode = CommonErrorCode.SUCCESSSFUL;
    }

    public SbcRuntimeException(Throwable cause){
        this("",cause);
    }

    public SbcRuntimeException(String errorCode, String result){
        super();
        this.result = result;
        this.errorCode = errorCode;
    }

    public SbcRuntimeException(String errorCode){
        super();
        this.errorCode = errorCode;
    }

    public SbcRuntimeException(String errorCode, Object[] params){
        super();
        this.errorCode = errorCode;
        this.params = params;
    }

    public SbcRuntimeException(String errorCode, Throwable cause){
        super();
        this.errorCode = errorCode;
    }

    public SbcRuntimeException(Object data, String errorCode){
        this.data = data;
        this.errorCode = errorCode;
    }

    public SbcRuntimeException(Object data, Throwable cause){
        super(cause);
        this.data = data;
    }



}
