package com.tof.deployment.dto.test;

import com.tof.deployment.dto.BaseResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WangSong
 * 2019-08-05
 */
public interface TestPost {
    @Data
    class Arg{
        private String data;
    }

    @Data
    @AllArgsConstructor
    class Result extends BaseResult{
        private String data;

    }
}
