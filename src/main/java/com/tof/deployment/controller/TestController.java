package com.tof.deployment.controller;

import com.tof.deployment.annotation.ForMD;
import com.tof.deployment.dto.BaseResult;
import com.tof.deployment.dto.test.TestPost;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WangSong
 * 2019-08-05
 */
@RestController
@RequestMapping("test")
public class TestController {
    @ForMD("测试get")
    @RequestMapping("get")
    public BaseResult testGet() {
        return BaseResult.getOkBaseResult();
    }

    @ForMD("测试post")
    @RequestMapping("post")
    public BaseResult testPost(@RequestBody TestPost.Arg arg) {
        return new TestPost.Result(arg.getData()).setOk();
    }
}
