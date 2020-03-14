package com.mybatis.generator.controller;

import com.mybatis.generator.common.CommonResponse;
import com.mybatis.generator.service.impl.MemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MemberController
 * @Author lzl
 * @Description TODO
 * @Date 2020/3/14 16:08
 * @Version 1.0
 **/
@Api(tags = "MemberController",description = "会员管理接口")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    @ApiOperation("获取验证码")
    @GetMapping(value = "/authCode")
    public CommonResponse getAuthCode(String telephone)
    {
        return memberService.generateAuthCode(telephone);
    }

    @ApiOperation("校验验证码")
    @PostMapping(value = "/verifyAuthCode")
    public CommonResponse verifyAuthCode(String telephone,String authCode)
    {
        return memberService.verifyAuthCode(telephone,authCode);
    }
}
