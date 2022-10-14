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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysRole;
import com.breeze.boot.system.dto.RoleDTO;

import java.util.List;

/**
 * 系统角色服务
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 用户角色列表
     *
     * @param id id
     * @return {@link List}<{@link SysRole}>
     */
    List<SysRole> listUserRole(Long id);

    /**
     * 列表页面
     *
     * @param roleDTO 角色dto
     * @return {@link Page}<{@link SysRole}>
     */
    Page<SysRole> listPage(RoleDTO roleDTO);

    /**
     * 删除由ids
     *
     * @param ids id
     * @return {@link Result}
     */
    Result deleteByIds(List<Long> ids);

}

