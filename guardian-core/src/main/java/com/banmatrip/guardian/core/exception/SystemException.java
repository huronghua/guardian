package com.banmatrip.guardian.core.exception;

/**
 * 业务异常
 */
public class SystemException extends Exception {
    /**
     * 构造方法
     */
    public SystemException() {
        super();
    }

    /**
     * @param message 异常消息
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * @param message 异常消息
     * @param cause   异常原因
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause 异常原因
     */
    public SystemException(Throwable cause) {
        super(cause);
    }
}