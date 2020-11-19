package com.common.sequence.exception;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:00
 */
public class SeqException extends RuntimeException {

    public SeqException(String message) {
        super(message);
    }

    public SeqException(Throwable cause) {
        super( cause);
    }
}
