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

package com.breeze.oss.config;

import com.breeze.oss.local.config.LocalProperties;
import com.breeze.oss.minio.config.BreezeMinioProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio属性
 *
 * @author gaoweixuan
 * @date 2023-02-27
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "breeze.oss")
public class StorageProperties {

    /**
     * 本地属性
     */
    private LocalProperties localProperties;

    /**
     * minio属性
     */
    private BreezeMinioProperties minioProperties;

}