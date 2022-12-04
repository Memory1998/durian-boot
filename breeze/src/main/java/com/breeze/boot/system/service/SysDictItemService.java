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

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysDictItem;
import com.breeze.boot.system.dto.DictSearchDTO;

import java.util.List;

/**
 * 系统字典项服务
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 字典列表项
     *
     * @param dictSearchDTO 字典搜索DTO
     * @return {@link List}<{@link SysDictItem}>
     */
    List<SysDictItem> listDictItem(DictSearchDTO dictSearchDTO);

    /**
     * 查询字典通过类型代码
     *
     * @param dictCode dict类型代码
     * @return {@link Result}<{@link List}<{@link SysDictItem}>>
     */
    Result<List<SysDictItem>> listDictByCode(String dictCode);
}

