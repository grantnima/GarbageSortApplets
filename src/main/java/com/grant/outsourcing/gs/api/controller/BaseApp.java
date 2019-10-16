package com.grant.outsourcing.gs.api.controller;

import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.constant.IRespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApp.class);

    protected Map<String, Object> buildResponse() {
        return buildResponse(ERespCode.SUCCESS, null);
    }

    /**
     * 封装请求成功响应
     *
     * @param data
     * @return
     */
    protected Map<String, Object> buildResponse(Object data) {
        return buildResponse(ERespCode.SUCCESS, data);
    }

    /**
     * 封装接口废弃响应
     *
     * @param version
     * @param reason
     * @return
     */
    protected Map<String, Object> buildDeprecatedResponse(String version, String reason) {
        Map<String, String> data = new HashMap<>();
        data.put("expired_version", version);
        data.put("reason", reason);
        return buildResponse(ERespCode.DEPRECATED, data);
    }

    protected Map<String, Object> buildResponse(IRespCode respCode, Object data) {
        return buildResponse(respCode.getCode(), respCode.getMessage(), data);
    }

    protected Map<String, Object> buildResponse(IRespCode respCode) {
        return buildResponse(respCode.getCode(), respCode.getMessage(), null);
    }

    /**
     * 封装统一的响应格式
     * {
     *     "ret_code": {ret_code},
     *     "message": {message},
     *     "data": {data_object}
     * }
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    protected Map<String, Object> buildResponse(int code, String message, Object data) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", code);
        responseMap.put("msg", message);
        responseMap.put("data", data);
        responseMap.put("timestamp", System.currentTimeMillis());
        return responseMap;
    }

}
