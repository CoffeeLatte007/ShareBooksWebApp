package com.lclizhao.sharebook.daoandservice;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.daomain.Book;
import com.lclizhao.sharebook.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:测试Service和Dao
 */
public class BookServiceTest {
    @Test
    public void testService(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService= (BookService) context.getBean("bookService");
        Book book=new Book();
        book.setBookName("深入理解Java虚拟机");
        book.setAuthor("周志明");
        book.setiSBN("232183217832");
        bookService.save(book);
    }
}
