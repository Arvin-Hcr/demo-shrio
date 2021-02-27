package com.aaa.lee.shiro.service;

import com.aaa.lee.shiro.mapper.BookMapper;
import com.aaa.lee.shiro.model.Book;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BookService
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/22
 * @Since version-1.0
 **/
@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public Map<String, Object> selectAllBook(){
        Map<String, Object> resultMap = new HashMap<>();
        List<Book> bookList = bookMapper.selectAll();
        if (bookList.size()>0){
            resultMap.put("code","200");
            resultMap.put("data",bookList);
        }else {
            resultMap.put("code","404");
            resultMap.put("msg","暂时没有图书!");
        }
        return resultMap;
    }

    /**
     * 新增图书
     * @param book
     * @return
     */
    //双重权限认证,判断用户有没有相应的权限
    @RequiresPermissions("book:insert")
    public String addBook(Book book){
        return null;
    }
}
