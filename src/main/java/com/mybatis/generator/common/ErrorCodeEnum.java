package com.mybatis.generator.common;



/**
 * @ClassName ErrorCodeEnum
 * @Author lzl
 * @Description 错误码枚举类
 * @Date 2020/3/14 10:16
 * @Version 1.0
 **/
public enum ErrorCodeEnum implements IErrorCode{
    SUCCESS(200,"success"),
    FAILED(500,"failure"),
    VALIDATE_FAILED(10001, "输入参数不合法"),
    NOT_EXISTS(10002,"查询无结果"),
    HAS_SEND(10003,"验证码已发送，请稍后"),
    CODE_ERROR(10004,"验证码错误"),
    ;

    private Integer code;

    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
