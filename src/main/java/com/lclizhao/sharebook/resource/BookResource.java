package com.lclizhao.sharebook.resource;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.daomain.Book;
import com.lclizhao.sharebook.daomain.User_Book;
import com.lclizhao.sharebook.resource.Exception.AppException;
import com.lclizhao.sharebook.service.BookService;
import com.lclizhao.utils.bookCheck;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:Book的资源类
 */
@Path("book")
public class BookResource  {
    @Autowired
    private BookService bookService;
    /**
     * <p>saveBook.</p>
     *
     * @param book 书.
     * @return Book 状态码 ISBN为null的均为自定义图书
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response save(final Book book) throws AppException{
        bookCheck.check(book);
        return Response.status(200).entity(bookService.save(book)).build();
    }

}
