package com.hcr.shiro.controller;

import com.hcr.shiro.model.Book;
import com.hcr.shiro.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: BookController
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/23
 * @Since version-1.0
 **/
@Controller
//@Api(value = "shiro",tags = "shiro-demo-project")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/allBooks")
   // @ApiOperation(value = "查询图书",notes = "查询图书")
    public String returnBookIndex(ModelMap modelMap){
        //把数据库中的数据返回给前端
        Map<String, Object> resultMap = bookService.selectAllBook();
        System.out.println("图书信息:" + resultMap);
        if ("200".equals((String)resultMap.get("code")) && null != resultMap.get("data")){
            modelMap.addAttribute("ListBook",(List<Book>)resultMap.get("data"));
            return "book_index";
        }else {
            return "404";
        }
    }

    @RequestMapping("/addBook")
    //@ApiOperation(value = "新增图书",notes = "新增图书")
    public String addBook(){
        Book book =new Book();
        bookService.addBook(book);
        return "";
    }
}
