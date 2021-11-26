package com.chenyi.yanhuohui.common.base.util;

import com.chenyi.yanhuohui.common.base.starter.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.Assert;

import java.util.Locale;

public final class MessageSourceUtil {
    private static MessageSource messageSource;

    static {
        init((MessageSource) SpringContextHolder.getBean(MessageSource.class));
    }

    public static void init(MessageSource messageSource){
        MessageSourceUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, Object[] args, Locale locale){
        Assert.notNull(messageSource,"MessageUtil not init ...");
        try {
            return messageSource.getMessage(code,args,locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale){
        Assert.notNull(messageSource,"MessageUtil not init ...");
        try {
            return messageSource.getMessage(resolvable,locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
