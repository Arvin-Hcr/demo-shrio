package com.aaa.lee.shiro.mapper;


import com.aaa.lee.shiro.model.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<String> selcetPermissionByUserName(String userName);
}