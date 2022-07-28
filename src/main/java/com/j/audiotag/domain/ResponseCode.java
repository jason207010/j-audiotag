package com.j.audiotag.domain;

import lombok.Getter;

/**
 * 消息类型
 * @author Jason
 * @since 2021-08-27 16:03:14
 */
@Getter
public enum ResponseCode {
    /************************* 系统相关 *************************/
    /**
     * 成功
     */
    SUCCESS("A0000", "成功"),
    /**
     * 系统异常
     */
    FAIL("A0001", "系统异常"),
    /**
     * 认证错误
     */
    UNAUTHORIZED("A0003", "认证错误"),
    /**
     * 账号已被停用
     */
    BLOCK("A0005", "账号已被停用"),
    /**
     * 非法签名
     */
    INVALID_TOKEN("A0006", "非法签名"),
    /**
     * 不支持的操作
     */
    OPERATION_NOT_SUPPORTED("A0007", "不支持的操作"),
    /**
     * 参数校验不通过
     */
    VALIDATE_FAIL("A0008", "参数校验不通过"),
    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE("A0009", "服务不可用"),
    /**
     * 404
     */
    HTTP_STATUS_404("A0010", "NOT FOUND"),
    /**
     * 404
     */
    HTTP_STATUS_401("A0011", "没有访问权限"),

    ;

    private String code;
    private String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode getByCode(String code) {
        for (ResponseCode responseCode : values()) {
            if(responseCode.code.equals(code)) {
                return responseCode;
            }
        }
        return null;
    }
}
