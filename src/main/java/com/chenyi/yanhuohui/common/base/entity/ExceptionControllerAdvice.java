package com.chenyi.yanhuohui.common.base.entity;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Objects;

@Slf4j
//这个注解配合@ExceptionHandler注解使用，捕获controller中的异常，应用到所有@RequestMapping中
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    private static final String LOGGER_FORMAT = "操作执行异常：异常编码：{}，异常信息{}，堆栈信息：";

    @ExceptionHandler(SbcRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse SbcRuntimeExceptionHandle(SbcRuntimeException ex){
        Throwable cause = ex.getCause();
        String msg = "";
        if(cause != null){
            msg = cause.getMessage();
        }
        String errorCode = ex.getErrorCode();
        if(StringUtils.hasText(errorCode)){
            if(Objects.isNull(ex.getResult())){
                msg = this.getMessage(errorCode,ex.getParams());
            }else {
                msg = ex.getResult();
            }

            //如果异常码在本项目中有定义，则用本项目中定义的提示信息
            if(!errorCode.equals(msg)){
                ex.setResult(msg);
            }

            if(StringUtils.hasText(ex.getResult()) && !"fail".equals(ex.getResult())){
                log.error(LOGGER_FORMAT,ex.getErrorCode(),ex.getResult(), ex);
                return BaseResponse.info(errorCode,ex.getResult(),ex.getData());
            }
        }else if(!StringUtils.hasText(msg)){
            msg = this.getMessage(CommonErrorCode.FAILED,ex.getParams());
        }

        if(!StringUtils.hasText(errorCode)){
            errorCode = CommonErrorCode.FAILED;
        }
        log.error(LOGGER_FORMAT,errorCode,msg,ex);

        return new BaseResponse(errorCode,ex.getParams());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public <T> BaseResponse<T> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new BaseResponse<>(CommonErrorCode.PARAMETER_ERROR);
    }

    protected String getMessage(String code, Object[] params){
        try {
            return messageSource.getMessage(code, params, Locale.CHINA);
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
            return code;
        }
    }
}
