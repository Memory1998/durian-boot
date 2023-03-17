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

package com.breeze.boot.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.domain.SysQuartzJobLog;
import com.breeze.boot.quartz.dto.JobDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Quartz任务日志映射器
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Mapper
public interface SysQuartzJobLogMapper extends BaseMapper<SysQuartzJobLog> {

    /**
     * 列表页面
     *
     * @param page   页面
     * @param jobDTO 任务DTO
     * @return {@link Page}<{@link SysQuartzJob}>
     */
    Page<SysQuartzJob> listPage(@Param("page") Page<SysQuartzJobLog> page, @Param("jobDTO") JobDTO jobDTO);

    /**
     * 清洁
     */
    void clean();

}




