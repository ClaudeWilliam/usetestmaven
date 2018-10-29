package com.qjq.guava.netty.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter @ToString
public class Response implements Serializable {

    private static final long serialVersionUID = 9159656996307829939L;

    private String responseId;

    private String message;

    private Date time;

}
