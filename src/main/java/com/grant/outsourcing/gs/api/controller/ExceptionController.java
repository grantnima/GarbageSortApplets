package com.grant.outsourcing.gs.api.controller;

import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.exception.InternalServerException;
import com.grant.outsourcing.gs.api.exception.MissingParamException;
import com.grant.outsourcing.gs.api.exception.ResourceNotFoundException;
import com.grant.outsourcing.gs.constant.ERespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends BaseApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseBody
    @ExceptionHandler(value = {MissingServletRequestParameterException.class, TypeMismatchException.class, MissingParamException.class,
            BaseException.class})
    public Map<String, Object> handleClientException(HttpServletRequest req, HttpServletResponse resp, Exception e) {

        LOGGER.error(String.format("handleClientException(HttpServletRequest, HttpServletResponse, Exception) - %s", e.getMessage()), e);

        if (e instanceof MissingParamException) {
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            MissingParamException missingParamE = (MissingParamException) e;
            return buildResponse(missingParamE.getCode(), missingParamE.getMessage(), null);

        } else if (e instanceof TypeMismatchException) {
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return buildResponse(ERespCode.TYPE_MISMATCH_EXCEPTION.getCode(),
                    ERespCode.TYPE_MISMATCH_EXCEPTION.getMessage(), null);

        } else if (e instanceof BaseException) {
            resp.setStatus(HttpStatus.OK.value());
            BaseException baseException = (BaseException) e;
            return buildResponse(baseException.getCode(), baseException.getMessage(), baseException.getData());

        } else if (e instanceof  MissingServletRequestParameterException) {
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return buildResponse(ERespCode.MISSING_PARAMETER, null);

        } else {
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return buildResponse(ERespCode.MISSING_PARAMETER, null);
        }
    }

    @ResponseBody
    @ExceptionHandler(value = InternalServerException.class)
    public Map<String, Object> handleServerException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        LOGGER.error(String.format("handleServerException(HttpServletRequest, HttpServletResponse, Exception) - %s", e.getMessage()), e);
        resp.setStatus(HttpStatus.OK.value());

        if (e instanceof InternalServerException) {
            InternalServerException missingParamE = (InternalServerException) e;
            return buildResponse(missingParamE.getCode(), missingParamE.getMessage(), missingParamE.getData());
        } else {
            return buildResponse(ERespCode.INTERNAL_ERROR);
        }
    }

    @ResponseBody
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public Map<String, Object> handleResourceNotFoundException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        LOGGER.error(String.format("handleResourceNotFoundException(HttpServletRequest, HttpServletResponse, Exception) - %s", e.getMessage()), e); //$NON-NLS-1$
        resp.setStatus(HttpStatus.NOT_FOUND.value());

        if (e instanceof ResourceNotFoundException) {
            ResourceNotFoundException missingParamE = (ResourceNotFoundException) e;
            return buildResponse(missingParamE.getCode(), missingParamE.getMessage(), missingParamE.getData());
        } else {
            return buildResponse(ERespCode.RESOURCE_NOT_FOUND);
        }

    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        LOGGER.error(String.format("handleException(HttpServletRequest, HttpServletResponse, Exception) - %s", e.getMessage()), e); //$NON-NLS-1$
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return buildResponse(ERespCode.HTTP_METHOD_NOT_SUPPORTED);
        } else {
            //未捕捉的内部服务异常
            LOGGER.warn(String.format("uncaught internal server exception, msg: %s", e.getMessage()), e);
            return buildResponse(ERespCode.INTERNAL_ERROR);
        }
    }

}