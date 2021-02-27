package com.arvin.shiro.mapper;

import com.arvin.shiro.model.Theacher;

import java.util.List;

public interface TheacherMapper {
    int insert(Theacher record);

    List<Theacher> selectAll();

    Theacher selectTeacherName(String userName);
}