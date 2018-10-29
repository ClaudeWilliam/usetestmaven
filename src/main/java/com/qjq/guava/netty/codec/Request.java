package com.qjq.guava.netty.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
public class Request implements Serializable {

    private static final long serialVersionUID = -8062292345242159139L;

    private  String requestId;

    private String message;

    private String host;

//    private byte[] attachment;

}
