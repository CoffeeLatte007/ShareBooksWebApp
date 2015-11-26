package com.lclizhao.sharebook.service.impl;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.dao.BookDao;
import com.lclizhao.sharebook.daomain.Book;
import com.lclizhao.sharebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Name:BookServiceImpl
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
@Service("bookService")
//设置统一的只读事务,具体的在下面细粒度配置
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService{
    @Autowired
    BookDao bookDao;
    @Override
    public List<Book> findBookBylist(Book book, int pageNo, int pageSize) {
        return null;
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = false)
    public void save(Book book) {
        bookDao.save(book);
    }
}
