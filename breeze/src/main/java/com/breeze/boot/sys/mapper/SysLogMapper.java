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

package com.breeze.boot.sys.mapper;

import com.breeze.boot.sys.domain.SysLog;
import com.breeze.database.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志映射器
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Mapper
public interface SysLogMapper extends BreezeBaseMapper<SysLog> {

    /**
     * 清空
     */
    void truncate();

}
