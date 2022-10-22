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

package com.breeze.boot.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysMenu;
import com.breeze.boot.system.dto.MenuDTO;
import com.breeze.boot.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统菜单控制器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@RestController
@RequestMapping("/sys/menu")
@Tag(name = "系统菜单管理模块", description = "SysMenuController")
public class SysMenuController {

    /**
     * 系统菜单服务
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result<? extends Object> list(@Validated @RequestBody MenuDTO menuDTO) {
        return this.sysMenuService.listMenu(menuDTO);
    }

    /**
     * 树形菜单
     *
     * @param platformCode 平台标识
     * @return {@link Result}
     */
    @Operation(summary = "树形菜单")
    @GetMapping("/listTreeMenu")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result<List<Tree<Long>>> listTreeMenu(@RequestParam(required = false) String platformCode) {
        return this.sysMenuService.listTreeMenu(platformCode);
    }

    /**
     * 树形权限列表
     *
     * @return {@link Result}
     */
    @Operation(summary = "树形权限列表")
    @GetMapping("/listTreePermission")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result<List<Tree<Long>>> listTreePermission() {
        return this.sysMenuService.listTreePermission();
    }

    /**
     * 信息
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:menu:info')")
    public Result<SysMenu> info(@PathVariable("id") Long id) {
        return Result.ok(sysMenuService.getById(id));
    }

    /**
     * 保存
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:menu:save')")
    public Result<Boolean> save(@Validated @RequestBody SysMenu menuEntity) {
        return this.sysMenuService.saveMenu(menuEntity);
    }

    /**
     * 更新
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "更新")
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:menu:update')")
    public Result<Boolean> update(@Validated @RequestBody SysMenu menuEntity) {
        return sysMenuService.updateMenuById(menuEntity);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:menu:delete')")
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long id) {
        return this.sysMenuService.deleteById(id);
    }

}
