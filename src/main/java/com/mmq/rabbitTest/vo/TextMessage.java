package com.mmq.rabbitTest.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/10.
 */
public class TextMessage implements Serializable {
    private static final long serialVersionUID = 1l;

    private String messageSource;

    private String messageDestination;

    private String content;

    private Date sendTime;

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessageDestination() {
        return messageDestination;
    }

    public void setMessageDestination(String messageDestination) {
        this.messageDestination = messageDestination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
