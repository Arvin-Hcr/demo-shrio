package com.hcr.shiro.mapper;



import com.hcr.shiro.model.Book;

import java.util.List;

public interface BookMapper {
    int insert(Book record);

    List<Book> selectAll();
}