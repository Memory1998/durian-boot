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

package com.breeze.boot.process.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.process.domain.ActReDeployment;
import com.breeze.boot.process.dto.ProcessDeploymentSearchDTO;
import com.breeze.boot.process.mapper.ActReDeploymentMapper;
import com.breeze.boot.process.service.ActReDeploymentService;
import com.breeze.boot.process.vo.DeploymentVO;
import org.springframework.stereotype.Service;

/**
 * 流程部署服务Impl
 *
 * @author gaoweixuan
 * @date 2023-03-08
 */
@Service
public class ActReDeploymentServiceImpl extends ServiceImpl<ActReDeploymentMapper, ActReDeployment> implements ActReDeploymentService {

    /**
     * 列表页面
     *
     * @param processDeploymentSearchDTO 搜索过程部署dto
     * @return {@link Page}<{@link DeploymentVO}>
     */
    @DS("flowable")
    @Override
    public Page<DeploymentVO> listPage(ProcessDeploymentSearchDTO processDeploymentSearchDTO) {
        return this.baseMapper.listPage(new Page<>(processDeploymentSearchDTO.getCurrent(), processDeploymentSearchDTO.getSize()), processDeploymentSearchDTO);
    }

}
