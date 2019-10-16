package com.grant.outsourcing.gs.constant;

/**
 * Grant 2019年10月16日
 */
public enum ERespCode implements IRespCode {

    // 请求成功
    SUCCESS("请求成功", 0),
    DEPRECATED("接口已废弃，请查询最新接口文档", 4),

    // 公共异常业务码
    MISSING_PARAMETER("缺少参数", 40001),
    TYPE_MISMATCH_EXCEPTION("参数格式不匹配", 40002),
    RESOURCE_NOT_FOUND("找不到相关资源", 40003),
    HTTP_METHOD_NOT_SUPPORTED("http方法不支持", 40004),
    ACTION_DUPLICATED("操作重复", 40005),
    EXPIRED_TOKEN("登录状态已过期", 40006),
    PERMISSION_DENIED("没有操作权限", 40007),
    INVALID_MOBILE("手机号格式不合法", 40008),
    INVALID_EMAIL("邮箱地址格式不合法", 40009),
    INVALID_PARAMETER("请求参数取值不合法", 40010),
    INVALID_PASSWORD("请输入6~16位的密码", 40011),
    INVALID_PLATFORM("不合法的应用平台", 40012),

    INVALID_OPEN_ID("不合法的openid", 40013),
    INVALID_TASK_TICKET("无效的任务请求", 40014),
    TASK_STATUS_IS_FINISHED("任务已完成，请勿重复操作", 40015),
    INVALID_HOMEPAGE_INFO("不合法的json结构", 40016),

    CANNOT_GET_PRIZE("未完成任务，不可领奖", 40017),
    PRIZE_WAS_RECEIVED("奖品已领取", 40018),

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

