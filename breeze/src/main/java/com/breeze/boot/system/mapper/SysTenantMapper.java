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

package com.breeze.boot.system.mapper;

import com.breeze.database.mapper.BreezeBaseMapper;
import com.breeze.boot.system.domain.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统租户映射器
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
@Mapper
public interface SysTenantMapper extends BreezeBaseMapper<SysTenant> {

}




