package com.aaa.lee.shiro.controller;

import com.aaa.lee.shiro.model.Book;
import com.aaa.lee.shiro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: bookController
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/22
 * @Since version-1.0
 **/
@Controller
public class bookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/allBooks")
    public String returnBookindex(ModelMap modelMap){
        //把数据库中的数据返回给前端
        Map<String, Object> resultMap = bookService.selectAllBook();
        if ("200".equals((String)resultMap.get("code")) && null != resultMap.get("data")){
            modelMap.addAttribute("ListBook",(List<String>)resultMap.get("data"));
            return "book_index";
        }else {
            return "404";
        }
    }

    @RequestMapping("/addBook")
    public String addBook(){
        Book book =new Book();
        bookService.addBook(book);
        return "";
    }
}
