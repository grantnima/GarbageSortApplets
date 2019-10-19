package com.grant.outsourcing.gs.constant;

/**
 * Grant 2019年10月16日
 */
public enum ERespCode implements IRespCode {

    // 请求成功
    SUCCESS("请求成功", 0),
    DEPRECATED("接口已废弃，请查询最新接口文档", 4),

    // 公共异常业务码
    EXPIRED_TOKEN("登录状态已过期", 40006),
    CODE_TO_SESSION_FAIL("微信获取登录凭证失败",40007),
    COLLECTED("垃圾已收藏",40008),

    // 内部异常业务码
    INTERNAL_ERROR("处理错误", 50001);
    private String message;
    private int code;

    ERespCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override public String getMessage() {
        return message;
    }

    @Override public int getCode() {
        return code;
    }
}

