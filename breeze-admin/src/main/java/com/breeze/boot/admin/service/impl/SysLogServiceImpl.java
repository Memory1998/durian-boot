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

package com.breeze.boot.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.LogDTO;
import com.breeze.boot.admin.entity.SysLogEntity;
import com.breeze.boot.admin.mapper.SysLogMapper;
import com.breeze.boot.admin.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统日志服务impl
 *
 * @author breeze
 * @date 2022-09-02
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    /**
     * 日志列表
     *
     * @param logDTO 日志dto
     * @return {@link Page}<{@link SysLogEntity}>
     */
    @Override
    public Page<SysLogEntity> listLog(LogDTO logDTO) {
        Page<SysLogEntity> logEntityPage = new Page<>(logDTO.getCurrent(), logDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(logDTO.getSystemModule()), SysLogEntity::getSystemModule, logDTO.getSystemModule())
                .like(StrUtil.isAllNotBlank(logDTO.getLogTitle()), SysLogEntity::getLogTitle, logDTO.getLogTitle())
                .eq(Objects.nonNull(logDTO.getDoType()), SysLogEntity::getDoType, logDTO.getDoType())
                .eq(Objects.nonNull(logDTO.getRequestType()), SysLogEntity::getRequestType, logDTO.getRequestType())
                .eq(Objects.nonNull(logDTO.getResult()), SysLogEntity::getResult, logDTO.getResult())
                .ge(Objects.nonNull(logDTO.getStartDate()), SysLogEntity::getCreateTime, logDTO.getStartDate())
                .le(Objects.nonNull(logDTO.getEndDate()), SysLogEntity::getCreateTime, logDTO.getEndDate())
                .page(logEntityPage);
    }
}