/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.Result;
import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.utils.SecurityUtils;
import com.breeze.boot.system.dto.MenuDTO;
import com.breeze.boot.system.entity.SysMenu;
import com.breeze.boot.system.entity.SysMenuRole;
import com.breeze.boot.system.entity.SysPlatform;
import com.breeze.boot.system.entity.SysRole;
import com.breeze.boot.system.mapper.SysMenuMapper;
import com.breeze.boot.system.service.SysMenuRoleService;
import com.breeze.boot.system.service.SysMenuService;
import com.breeze.boot.system.service.SysPlatformService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统菜单服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 平台服务
     */
    @Autowired
    private SysPlatformService platformService;

    /**
     * 服务菜单作用
     */
    @Autowired
    private SysMenuRoleService menuRoleService;

    /**
     * 用户菜单权限列表
     *
     * @param roleEntityList 角色实体列表
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> listUserMenuPermission(List<SysRole> roleEntityList) {
        return this.baseMapper.listUserMenuPermission(roleEntityList);
    }

    /**
     * 树菜单列表
     *
     * @param platformCode 平台代码
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreeMenu(String platformCode) {
        CurrentLoginUser currentLoginUser = SecurityUtils.getCurrentLoginUser();
        log.info("用户信息 ===> {}", JSONUtil.parse(currentLoginUser));
        if (CollUtil.isEmpty(currentLoginUser.getUserRoleIds())) {
            return Result.ok();
        }
        List<SysMenuRole> menuRoleList = this.menuRoleService.list(Wrappers.<SysMenuRole>lambdaQuery()
                .in(SysMenuRole::getRoleId, currentLoginUser.getUserRoleIds()));
        if (CollUtil.isEmpty(menuRoleList)) {
            return Result.ok();
        }
        List<Long> menuIdList = menuRoleList.stream().map(SysMenuRole::getMenuId).collect(Collectors.toList());
        // 使用CODE显示前端菜单
        SysPlatform platformEntity = this.platformService.getOne(Wrappers.<SysPlatform>lambdaQuery()
                .eq(SysPlatform::getPlatformCode, platformCode));
        if (Objects.isNull(platformEntity)) {
            return Result.ok();
        }
        return this.listTreeMenuData(menuIdList, platformEntity);
    }

    /**
     * 权限数据列表树作用
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    private Result<List<Tree<Long>>> listTreeRolePermissionData() {
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>lambdaQuery()
                .in(SysMenu::getType, "folder", "menu", "btn")
                .orderByAsc(SysMenu::getSort));
        if (CollUtil.isEmpty(menuList)) {
            return Result.ok();
        }
        return Result.ok(getTrees(menuList, 0L));
    }

    /**
     * 列表树菜单数据
     *
     * @param platformEntity 平台实体
     * @param menuIdList     菜单id列表
     * @return {@link Result}
     */
    private Result<List<Tree<Long>>> listTreeMenuData(List<Long> menuIdList, SysPlatform platformEntity) {
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>lambdaQuery()
                .in(SysMenu::getId, menuIdList)
                .eq(SysMenu::getPlatformId, platformEntity.getId())
                .in(SysMenu::getType, "folder", "menu")
                .orderByAsc(SysMenu::getSort));
        if (CollUtil.isEmpty(menuList)) {
            return Result.ok();
        }
        return Result.ok(getTrees(menuList, 0L));
    }

    /**
     * 菜单列表
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}<{@link ?} {@link extends} {@link Object}>
     */
    @Override
    public Result<? extends Object> listMenu(MenuDTO menuDTO) {
        if (StrUtil.isAllNotBlank(menuDTO.getName()) || StrUtil.isAllNotBlank(menuDTO.getTitle())) {
            List<SysMenu> entityList = this.baseMapper.listMenu(menuDTO);
            return Result.ok(entityList);
        }
        List<SysMenu> menuEntityList = this.baseMapper.listMenu(menuDTO);
        List<Tree<Long>> build = getTrees(menuEntityList, 0L);
        return Result.ok(build);
    }

    /**
     * 列表树许可
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> listTreePermission() {
        CurrentLoginUser currentLoginUser = SecurityUtils.getCurrentLoginUser();
        log.info("用户信息 ===> {}", JSONUtil.parse(currentLoginUser));
        if (CollUtil.isEmpty(currentLoginUser.getUserRoleIds())) {
            return Result.ok();
        }
        return this.listTreeRolePermissionData();
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result<Boolean> deleteById(Long id) {
        List<SysMenu> menuEntityList = this.list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuEntityList)) {
            return Result.warning(Boolean.FALSE, "存在子菜单, 不可删除");
        }
        boolean remove = this.removeById(id);
        if (remove) {
            return Result.ok(Boolean.TRUE, "删除成功");
        }
        return Result.fail(Boolean.FALSE, "删除失败");
    }

    @Override
    public Result<Boolean> saveMenu(SysMenu menuEntity) {
        SysMenu sysMenu = this.getById(menuEntity.getParentId());
        if (!Objects.equals(0L, menuEntity.getParentId()) && Objects.isNull(sysMenu)) {
            return Result.fail("上一层组件不存在");
        }
        return Result.ok(this.save(menuEntity));
    }

    /**
     * 让树
     *
     * @param menuEntityList 菜单实体列表
     * @param id
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    private List<Tree<Long>> getTrees(List<SysMenu> menuEntityList, Long id) {
        List<TreeNode<Long>> collect = menuEntityList.stream().map(menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            Map<String, Object> leafMap = Maps.newHashMap();
            leafMap.put("sid", menu.getId().toString());
            leafMap.put("title", menu.getTitle());
            leafMap.put("path", menu.getPath());
            leafMap.put("component", menu.getComponent());
            leafMap.put("icon", menu.getIcon());
            leafMap.put("platformName", menu.getPlatformName());
            leafMap.put("permission", menu.getPermission());
            leafMap.put("platformId", menu.getPlatformId());
            leafMap.put("sort", menu.getSort());
            leafMap.put("type", menu.getType());
            node.setExtra(leafMap);
            return node;
        }).collect(Collectors.toList());
        return TreeUtil.build(collect, id);
    }
}