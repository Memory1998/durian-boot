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

package com.breeze.oss;

import com.breeze.core.enums.ResultCode;
import com.breeze.core.ex.SystemServiceException;
import com.breeze.oss.bo.FileBO;
import com.breeze.oss.local.service.LocalFileService;
import com.breeze.oss.minio.service.MinioService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * minio 请求服务
 *
 * @author gaoweixuan
 * @date 2022-11-20
 */
@Slf4j
public class OssStoreService {

    /**
     * 本地文件服务
     */
    @Autowired
    private LocalFileService localFileService;

    /**
     * minio服务
     */
    @Autowired
    private MinioService minioService;

    /**
     * 下载
     *
     * @param ossStyle 存储方式
     * @param path     路径
     * @param fileName 文件名称
     * @param response response
     */
    public void download(Integer ossStyle, String path, String fileName, HttpServletResponse response) {
        switch (ossStyle) {
            case 0:
                beanIsExists(Objects.isNull(this.localFileService), "未配置本地存储方式");
                this.localFileService.download(path, fileName, response);
                break;
            case 1:
                beanIsExists(Objects.isNull(this.minioService), "未配置minio存储方式");
                this.minioService.download(path, fileName, response);
                break;
            default:
                log.error("存储类型错误");
        }
    }

    /**
     * 上传
     *
     * @param ossStyle    存储方式
     * @param file        文件
     * @param path        路径
     * @param newFileName 新文件名字
     * @return {@link FileBO}
     */
    @SneakyThrows
    public FileBO upload(Integer ossStyle, MultipartFile file, String path, String newFileName) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        FileBO fileBO;
        switch (ossStyle) {
            case 0:
                fileBO = this.localFileService.uploadFile(file, path, getNewFileName(newFileName, originalFileName, extension));
                break;
            case 1:
                fileBO = this.minioService.upload2Minio(file, path, getNewFileName(newFileName, originalFileName, extension));
                break;
            default:
                fileBO = null;
                log.error("存储类型错误");
        }
        return fileBO;
    }

    /**
     * 获得新文件名
     *
     * @param newFileName      新文件名字
     * @param originalFileName 原始文件名字
     * @param extension        扩展
     * @return {@link String}
     */
    @NotNull
    private String getNewFileName(String newFileName, String originalFileName, String extension) {
        return originalFileName.replace(extension, "") + "-" + newFileName + extension;
    }

    /**
     * 图片预览
     *
     * @param ossStyle 存储方式
     * @param pathName 路径 + 名称
     * @return {@link String}
     */
    public String preview(Integer ossStyle, String pathName) {
        String preView = "";
        switch (ossStyle) {
            case 0:
                this.beanIsExists(Objects.isNull(this.localFileService), "未配置本地存储方式");
                preView = this.localFileService.previewImg(pathName);
                break;
            case 1:
                this.beanIsExists(Objects.isNull(this.minioService), "未配置minio存储方式");
                preView = this.minioService.previewImg(pathName);
                break;
            default:
                log.error("存储类型错误");
        }
        return preView;
    }

    /**
     * bean是否存在
     *
     * @param aNull 是否空
     * @param msg   信息
     */
    private void beanIsExists(boolean aNull, String msg) {
        if (aNull) {
            throw new SystemServiceException(ResultCode.exception(msg));
        }
    }

    /**
     * 删除
     *
     * @param ossStyle 存储方式
     * @param path     路径 + 名称
     * @return {@link Boolean}
     */
    public Boolean remove(Integer ossStyle, String path) {
        boolean remove = Boolean.FALSE;
        switch (ossStyle) {
            case 0:
                beanIsExists(Objects.isNull(this.localFileService), "未配置本地存储方式");
                remove = this.localFileService.remove(path);
                break;
            case 1:
                beanIsExists(Objects.isNull(this.minioService), "未配置minio存储方式");
                remove = this.minioService.remove(path);
                break;
            default:
                log.error("存储类型错误");
        }
        return remove;
    }

}