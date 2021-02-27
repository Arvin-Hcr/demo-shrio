package com.aaa.lee.shiro.mapper;


import com.aaa.lee.shiro.model.Book;

import java.util.List;

public interface BookMapper {
    int insert(Book record);

    List<Book> selectAll();
}