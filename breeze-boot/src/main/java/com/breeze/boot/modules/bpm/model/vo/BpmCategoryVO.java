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

package com.breeze.boot.modules.bpm.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 流程分类VO
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程分类VO")
public class BpmCategoryVO {

    @Schema(description = "主键")
    private Long id;

    /**
     * 流程分类编码
     */
    @Schema(description = "流程分类编码")
    private String categoryCode;

    /**
     * 流程分类名称
     */
    @Schema(description = "流程分类名称")
    private String categoryName;

    /**
     * 流程分类名称
     */
    @Schema(description = "租户ID 审批流数据源下的表手动维护此字段")
    private Long tenantId;

}