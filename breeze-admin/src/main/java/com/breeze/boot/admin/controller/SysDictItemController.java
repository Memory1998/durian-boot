/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.admin.controller;

import com.breeze.boot.admin.dto.DictDTO;
import com.breeze.boot.admin.entity.SysDictItemEntity;
import com.breeze.boot.admin.entity.SysFileEntity;
import com.breeze.boot.admin.service.SysDictItemService;
import com.breeze.boot.core.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统字典控制器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Api(tags = "字典管理模块", value = "字典管理模块")
@RestController
@RequestMapping("/sys/dictItem")
public class SysDictItemController {

    /**
     * 系统字典服务
     */
    @Autowired
    private SysDictItemService sysDictItemService;

    /**
     * 列表
     *
     * @param dictDTO 字典dto
     * @return {@link Result}<{@link List}<{@link SysFileEntity}>>
     */
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:dict:list')")
    public Result list(@RequestBody DictDTO dictDTO) {
        return Result.ok(this.sysDictItemService.listDictItem(dictDTO));
    }

    /**
     * 保存
     *
     * @param dictItemEntity 字典项保存DTO
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:dict:save')")
    public Result save(@RequestBody SysDictItemEntity dictItemEntity) {
        return Result.ok(sysDictItemService.save(dictItemEntity));
    }

    /**
     * 更新
     *
     * @param dictItemEntity 字典项更新 实体
     * @return {@link Result}<{@link Boolean}>
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:dict:update')")
    public Result update(@RequestBody SysDictItemEntity dictItemEntity) {
        return Result.ok(this.sysDictItemService.updateById(dictItemEntity));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:dict:delete')")
    public Result delete(@RequestBody Long[] ids) {
        return Result.ok(this.sysDictItemService.removeByIds(Arrays.asList(ids)));
    }

}