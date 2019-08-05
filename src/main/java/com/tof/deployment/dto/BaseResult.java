package com.tof.deployment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WangSong
 * 2019-08-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {
    private int code;
    private String message;

    public BaseResult setOk() {
        this.code = 0;
        this.message = "OK";
        return this;
    }

    public BaseResult setError() {
        this.code = -1;
        this.message = "ERROR";
        return this;
    }

    public BaseResult setError(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public static BaseResult getOkBaseResult() {
        BaseResult baseResult = new BaseResult();
        baseResult.setOk();
        return baseResult;
    }

    public static BaseResult getErrorBaseResult() {
        BaseResult baseResult = new BaseResult();
        baseResult.setError();
        return baseResult;
    }

    public static BaseResult getErrorBaseResult(int code, String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.setError(code, message);
        return baseResult;
    }
}
