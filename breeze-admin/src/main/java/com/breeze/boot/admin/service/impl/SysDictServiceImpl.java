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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.DictDTO;
import com.breeze.boot.admin.entity.SysDictEntity;
import com.breeze.boot.admin.entity.SysDictItemEntity;
import com.breeze.boot.admin.mapper.SysDictMapper;
import com.breeze.boot.admin.service.SysDictItemService;
import com.breeze.boot.admin.service.SysDictService;
import com.breeze.boot.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统字典服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictEntity> implements SysDictService {

    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    public Page<SysDictEntity> listDict(DictDTO dictDto) {
        return this.baseMapper.listDict(new Page<>(dictDto.getCurrent(), dictDto.getSize()), dictDto);
    }

    /**
     * 开关
     *
     * @param dictDTO 字典 dto
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(DictDTO dictDTO) {
        return this.update(Wrappers.<SysDictEntity>lambdaUpdate()
                .set(SysDictEntity::getIsOpen, dictDTO.getIsOpen())
                .eq(SysDictEntity::getId, dictDTO.getId()));
    }

    @Override
    public Result deleteByIds(List<Long> ids) {
        boolean remove = this.sysDictItemService.remove(Wrappers.<SysDictItemEntity>lambdaQuery().in(SysDictItemEntity::getDictId, ids));
        if (remove) {
            this.removeByIds(ids);
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}