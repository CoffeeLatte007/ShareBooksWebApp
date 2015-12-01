package com.lclizhao.sharebook.resource;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.daomain.Book;
import com.lclizhao.sharebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:Book的资源类
 */
@Path("books")
public class BookResource  {
    @Autowired
    private BookService bookService;
    /**
     * <p>saveBook.</p>
     *
     * @param book 书.
     * @return Book
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Book save(final Book book){
        if(book!=null&&book.getBookName()!=null){
            return bookService.save(book);
        }
        book.setBookName("添加失败");
        return book;
    }

}
