/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.core.enums;

import lombok.Getter;

/**
 * 返回结果代码
 *
 * @author breeze
 * @date 2021/10/1
 */
@Getter
public enum ResultCode {

    /**
     * 客户端身份验证失败
     */
    CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),
    /**
     * 错误用户名或密码
     */
    WRONG_USERNAME_OR_PASSWORD(1002, "用户名或者密码错误"),
    /**
     * 格兰特不支持类型
     */
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),
    /**
     * 令牌无效
     */
    TOKEN_INVALID(1004, "验证失效"),
    /**
     * 用户名没有发现异常
     */
    USERNAME_NOT_FOUND_EXCEPTION(1005, "用户名或者密码错误"),
    /**
     * 假装内部身份验证服务异常
     */
    FEIGN_INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION(5003, "内部身份验证服务异常"),
    /**
     * 未授权资源，请联系管理员授权
     */
    UNAUTHORIZED(401, "未授权资源，请联系管理员授权"),
    /**
     * 资源不可用
     */
    FORBIDDEN(403, "资源不可用"),
    /**
     * 400
     */
    PARAMETER_IS_INCORRECT(400, "参数不正确"),
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    /**
     * 成功
     */
    OK(1, "请求成功"),
    /**
     * 警告
     */
    WARNING(2, "请求不合法"),
    /**
     * 失败
     */
    FAIL(0, "请求失败");

    /**
     * 代码
     */
    private final int code;

    /**
     * msg
     */
    private final String msg;

    /**
     * 返回结果代码
     *
     * @param code 代码
     * @param msg  msg
     */
    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
