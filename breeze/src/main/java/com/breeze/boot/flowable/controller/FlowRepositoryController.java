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

package com.breeze.boot.flowable.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.core.utils.Result;
import com.breeze.boot.flowable.dto.FlowRepositoryDTO;
import com.breeze.boot.flowable.service.IFlowRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程资源管理控制器
 *
 * @author gaoweixuan
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/flow/repository")
@Tag(name = "流程资源管理模块", description = "FlowRepositoryController")
public class FlowRepositoryController {


    @Autowired
    private IFlowRepositoryService flowRepositoryService;

    /**
     * 列表
     *
     * @param flowRepositoryDTO 流程资源查询DTO
     * @return {@link Result}<{@link IPage}<{@link ProcessDefinition}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:repository:list')")
    public Result<IPage<ProcessDefinition>> list(@RequestBody FlowRepositoryDTO flowRepositoryDTO) {
        return Result.ok(this.flowRepositoryService.listPage(flowRepositoryDTO));
    }

}
