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

package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户映射器
 *
 * @author breeze
 * @date 2022-08-31
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 选择用户
     *
     * @param page    对象页面
     * @param userDTO 用户dto
     * @return {@link IPage}<{@link SysUser}>
     */
    IPage<SysUser> listPage(Page<Object> page, @Param("userDTO") UserDTO userDTO);

}
