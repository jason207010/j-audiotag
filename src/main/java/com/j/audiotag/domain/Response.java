package com.j.audiotag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jason
 * @since 2021-08-27 16:03:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private String code;

    private String msg;

    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode().equals(code);
    }

    public static <T> Response<T> response(ResponseCode responseCode) {
        return response(responseCode, null, null);
    }

    public static <T> Response<T> response(ResponseCode responseCode, String msg) {
        return new Response<>(responseCode.getCode(), msg != null ? msg : responseCode.getMsg(), null);
    }

    public static <T> Response<T> response(ResponseCode responseCode, String msg, T data) {
        return new Response<>(responseCode.getCode(), msg != null ? msg : responseCode.getMsg(), data);
    }

    public static <T> Response<T> success() {
        return response(ResponseCode.SUCCESS, null);
    }

    public static <T> Response<T> success(T data) {
        return response(ResponseCode.SUCCESS, null, data);
    }

    public static <T> Response<T> fail() {
        return response(ResponseCode.FAIL, null);
    }

    public static <T> Response<T> fail(T data) {
        return response(ResponseCode.FAIL, null, data);
    }
}
