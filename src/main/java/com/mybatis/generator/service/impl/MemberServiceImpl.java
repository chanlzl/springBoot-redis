package com.mybatis.generator.service.impl;

import com.mybatis.generator.common.CommonResponse;
import com.mybatis.generator.common.ErrorCodeEnum;
import com.mybatis.generator.config.RedisConsts;
import com.mybatis.generator.service.MemberService;
import com.mybatis.generator.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;


/**
 * @ClassName MemberServiceImpl
 * @Author lzl
 * @Description TODO
 * @Date 2020/3/14 16:15
 * @Version 1.0
 **/
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private RedisUtil redisUtil;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Integer AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResponse generateAuthCode(String telephone) {
        Boolean exists = redisUtil.exists(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        if (exists)
        {
            LOGGER.info("authCode has existed");
            return CommonResponse.response(ErrorCodeEnum.HAS_SEND);
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        redisUtil.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,sb.toString(), RedisConsts.DATEBASE0);
        redisUtil.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS,RedisConsts.DATEBASE0);
        return CommonResponse.success(sb.toString());
    }

    @Override
    public CommonResponse verifyAuthCode(String telephone, String authCode) {
        String redisAuthCode = redisUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone, RedisConsts.DATEBASE0);
        if (authCode.equals(redisAuthCode))
        {
            return CommonResponse.success(null);
        }
        LOGGER.error("authCode is incorrect");
        return CommonResponse.response(ErrorCodeEnum.CODE_ERROR);
    }
}
