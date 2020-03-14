package com.mybatis.generator.service;

import com.mybatis.generator.common.CommonResponse;

public interface MemberService {

    CommonResponse generateAuthCode(String telephone);

    CommonResponse verifyAuthCode(String telephone,String authCode);
}
