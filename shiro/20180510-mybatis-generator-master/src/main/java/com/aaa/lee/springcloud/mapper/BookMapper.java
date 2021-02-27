package com.aaa.lee.springcloud.mapper;

import com.aaa.lee.springcloud.model.Book;
import java.util.List;

public interface BookMapper {
    int insert(Book record);

    List<Book> selectAll();
}