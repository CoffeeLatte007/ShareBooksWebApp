package com.lclizhao.sharebook.resource;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.daomain.Book;
import com.lclizhao.sharebook.daomain.User_Book;
import com.lclizhao.sharebook.resource.Exception.AppException;
import com.lclizhao.sharebook.service.BookService;
import com.lclizhao.sharebook.service.CollectionService;
import com.lclizhao.sharebook.service.UserService;
import com.lclizhao.utils.BookCheckUtils;
import com.lclizhao.utils.CollectionCheck;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:Book的资源类
 */
@Path("/book")
public class BookResource  {
    private final org.apache.logging.log4j.Logger logger= LogManager.getLogger(BookResource.class.getName());
    @Autowired
    private BookService bookService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private  UserService userService;
    /**
     * <p>saveBook.</p>
     *
     * @param book 书.
     * @return Book 状态码 ISBN为null的均为自定义图书
     */
    @Context
    SecurityContext securityContext;
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response save(final Book book) throws AppException{
        BookCheckUtils.check(book);
        return Response.status(201).entity(bookService.save(book)).build();
    }
    /**
     * 添加用户对图书的收藏
     * POST方法
     * @param bookId  需要收藏该本书的ID
     * @param user_book 该本图书的收藏信息
     * @return Response 返回响应体
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("/collection/{bookId:[0-9A-Za-z]{32}}")
    public Response addCollection(@PathParam("bookId")final String bookId,User_Book user_book) throws AppException{
        if(bookService.getByid(bookId)==null){
            String message="The "+bookId+" can not found";
            logger.error(message);
            throw new AppException(404,2004,"link",message,message);
        }
        if(user_book==null){
            user_book=new User_Book();
            user_book.setPrivcy("public");
            user_book.setRating(5);
        }
        else
        {
            CollectionCheck.check(user_book);
        }
        String tel=securityContext.getUserPrincipal().getName();
        String userId=userService.findUserByTel(tel).getUserID();
        user_book.setBookId(bookId);
        user_book.setUserId(userId);
        User_Book collection=collectionService.save(user_book);
        return Response.status(201).entity(collection).build();
    }
    /**
     * 更新用户对图书的收藏
     * PUT方法
     * @param bookId  需要更改该本书的ID
     * @param user_book 该本图书的收藏信息
     * @return Response 返回响应体
     */
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("/collection/{bookId:[0-9A-Za-z]{32}}")
    public Response modifycollection(@PathParam("bookId")final String bookId,User_Book user_book) throws AppException{
        if(bookService.getByid(bookId)==null){
            String message="The "+bookId+" can not found";
            logger.error(message);
            throw new AppException(404,2004,"link",message,message);
        }
        if(user_book==null){
            user_book=new User_Book();
            user_book.setPrivcy("public");
            user_book.setRating(5);
        }
        else
        {
            CollectionCheck.check(user_book);
        }
        String tel=securityContext.getUserPrincipal().getName();
        String userId=userService.findUserByTel(tel).getUserID();
        user_book.setBookId(bookId);
        user_book.setUserId(userId);
        User_Book collection=collectionService.update(user_book);
        return Response.status(202).entity(collection).build();

    }
    /**
     * 删除用户对图书的收藏
     * Delete方法
     * @param bookId  需要删除该本书的ID
     * @return Response 返回响应体，删除成功返回204
     */
    @DELETE
    @Path("/collection/{bookId:[0-9A-Za-z]{32}}")
    public Response deletecollection(@PathParam("bookId")final String bookId) throws AppException{
        if(bookService.getByid(bookId)==null){
            String message="The "+bookId+" can not found";
            logger.error(message);
            throw new AppException(404,2004,"link",message,message);
        }
        String tel=securityContext.getUserPrincipal().getName();
        String userId=userService.findUserByTel(tel).getUserID();
        collectionService.delete(bookId,userId);
        return Response.status(204).entity("deleted book failed id="+bookId).type("application/json").build();
    }
}
