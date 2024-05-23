/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.modules.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.query.PostQuery;
import com.breeze.boot.modules.auth.service.SysPostService;
import com.breeze.boot.modules.system.model.entity.SysPost;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * 系统岗位控制器
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/post")
@Tag(name = "系统岗位管理模块", description = "SysPostController")
public class SysPostController {

    /**
     * 系统岗位服务
     */
    private final SysPostService sysPostService;

    /**
     * 列表
     *
     * @param postQuery 岗位查询
     * @return {@link Result}<{@link IPage}<{@link SysPost}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('auth:post:list')")
    public Result<IPage<SysPost>> list(PostQuery postQuery) {
        return Result.ok(this.sysPostService.listPage(postQuery));
    }

    /**
     * 详情
     *
     * @param postId 平台id
     * @return {@link Result}<{@link SysPost}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{postId}")
    @PreAuthorize("hasAnyAuthority('auth:post:info')")
    public Result<SysPost> info(@PathVariable("postId") Long postId) {
        return Result.ok(this.sysPostService.getById(postId));
    }

    /**
     * 校验岗位编码是否重复
     *
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return {@link Result}<{@link SysPost}>
     */
    @Operation(summary = "校验岗位编码是否重复")
    @GetMapping("/checkPostCode")
    @PreAuthorize("hasAnyAuthority('auth:post:list')")
    public Result<Boolean> checkPostCode(@NotBlank(message = "岗位编码不能为空") @RequestParam("postCode") String postCode,
                                         @RequestParam(value = "postId", required = false) Long postId) {
        return Result.ok(Objects.isNull(this.sysPostService.getOne(Wrappers.<SysPost>lambdaQuery()
                .ne(Objects.nonNull(postId), SysPost::getId, postId)
                .eq(SysPost::getPostCode, postCode))));
    }

    /**
     * 创建
     *
     * @param post 平台实体入参
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('auth:post:create')")
    @BreezeSysLog(description = "岗位信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysPost post) {
        return Result.ok(this.sysPostService.save(post));
    }

    /**
     * 修改
     *
     * @param sysPost 平台实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('auth:post:modify')")
    @BreezeSysLog(description = "岗位信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysPost sysPost) {
        return Result.ok(this.sysPostService.updateById(sysPost));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('auth:post:delete')")
    @BreezeSysLog(description = "岗位信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysPostService.removePostByIds(ids);
    }

}
