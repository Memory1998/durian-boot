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

package com.breeze.boot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.model.entity.SysDictItem;
import com.breeze.boot.modules.system.model.query.DictItemQuery;
import com.breeze.boot.modules.system.mapper.SysDictItemMapper;
import com.breeze.boot.modules.system.service.SysDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统字典项服务impl
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService, InitializingBean {

    /**
     * 字典列表项
     *
     * @param dictItemQuery 字典项查询
     * @return {@link List}<{@link SysDictItem}>
     */
    @Override
    public List<SysDictItem> listDictItem(DictItemQuery dictItemQuery) {
        return this.baseMapper.listDictDetailByDictId(dictItemQuery.getDictId());
    }

    /**
     * 查询字典通过代码
     *
     * @param dictCodes dict类型代码
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link Map}<{@link String}, {@link Object}>>>>
     */
    @Override
    public Result<Map<String, List<Map<String, Object>>>> listDictByCodes(List<String> dictCodes) {
        List<Map<String, Object>> dictItemListMap = this.baseMapper.listDictByCodes(dictCodes);
        Map<String, List<Map<String, Object>>> resultMap = dictItemListMap.stream().collect(Collectors.groupingBy(dict -> (String) dict.get("dictCode")));
        return Result.ok(resultMap);
    }

    /**
     * 查询字典通过代码
     *
     * @param dictCode 字典编码
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> listDictByCode(String dictCode) {
        return Result.ok(this.baseMapper.listDictByCode(dictCode));
    }

    @Override
    public void afterPropertiesSet() {
    }

}
