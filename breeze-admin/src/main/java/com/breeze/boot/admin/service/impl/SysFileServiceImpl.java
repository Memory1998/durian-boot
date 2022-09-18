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
import com.breeze.boot.admin.dto.FileDTO;
import com.breeze.boot.admin.entity.SysFileEntity;
import com.breeze.boot.admin.mapper.SysFileMapper;
import com.breeze.boot.admin.service.SysFileService;
import org.springframework.stereotype.Service;

/**
 * 系统文件服务impl
 *
 * @author breeze
 * @date 2022-09-02
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFileEntity> implements SysFileService {

    /**
     * 列表文件
     *
     * @param fileDTO 日志dto
     * @return {@link Page}<{@link SysFileEntity}>
     */
    @Override
    public Page<SysFileEntity> listFile(FileDTO fileDTO) {
        Page<SysFileEntity> logEntityPage = new Page<>(fileDTO.getCurrent(), fileDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(fileDTO.getNewFileName()), SysFileEntity::getNewFileName, fileDTO.getNewFileName())
                .like(StrUtil.isAllNotBlank(fileDTO.getOldFileName()), SysFileEntity::getOldFileName, fileDTO.getOldFileName())
                .like(StrUtil.isAllNotBlank(fileDTO.getUrl()), SysFileEntity::getUrl, fileDTO.getUrl())
                .like(StrUtil.isAllNotBlank(fileDTO.getUserCode()), SysFileEntity::getUserCode, fileDTO.getUserCode())
                .like(StrUtil.isAllNotBlank(fileDTO.getUsername()), SysFileEntity::getUsername, fileDTO.getUsername())
                .page(logEntityPage);
    }
}