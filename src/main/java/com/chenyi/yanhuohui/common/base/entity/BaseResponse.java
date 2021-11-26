package com.chenyi.yanhuohui.common.base.entity;

import com.chenyi.yanhuohui.common.base.util.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

@Data
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {

    private String code;
    private String message;
    private Object errorData;
    private T context;

    public static <T> BaseResponse<T> SUCCESSFUL() {
        return new BaseResponse<>(CommonErrorCode.SUCCESSSFUL);
    }

    public static <T> BaseResponse<T> FAILED() {
        return new BaseResponse<>(CommonErrorCode.FAILED);
    }

    public BaseResponse(String code){
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code,null, LocaleContextHolder.getLocale());
    }

    public BaseResponse(String code, Object[] args){
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code,args, LocaleContextHolder.getLocale());
    }

    public BaseResponse(String code, String message, Object errorData, T context){
        this.code = code;
        this.message = message;
        this.errorData = errorData;
        this.context = context;
    }


    public static <T> BaseResponse<T> error(String message){
        return new BaseResponse<>(CommonErrorCode.FAILED, message, null, null);
    }

    public static <T> BaseResponse<T> success(T context){
        return new BaseResponse<>(CommonErrorCode.SUCCESSSFUL,null,null,context);
    }

    public static <T> BaseResponse<T> info(String errorCode, String message){
        return new BaseResponse<>(errorCode,message,null, null);
    }

    public static <T> BaseResponse<T> info(String errorCode, String message, Object obj){
        return new BaseResponse<>(errorCode,message,obj, null);
    }

}
