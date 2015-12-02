package com.lclizhao.sharebook.daomain;/**
 * Created by lizhaoz on 2015/11/26.
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Date;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
public class BookTest {
    @Test
    public void save1(){
        Session session=HibernateUtils.sessionFactory.openSession();
        Book book=new Book();
        book.setBookName("java编程思想");
        book.setiSBN("23217389217389");
        Transaction t=session.beginTransaction();
        session.save(book);
        t.commit();
        session.close();
    }
    @Test
    public void save2(){
        Session session=HibernateUtils.sessionFactory.openSession();
        User user=new User();
        user.setTelphone("18428368642");
        user.setPassword("a123456");
        user.setRegDate(new Date());
        Transaction t=session.beginTransaction();
        session.save(user);
        t.commit();
        session.close();
    }
    @Test
    public void save3(){
        Session session=HibernateUtils.sessionFactory.openSession();


        Transaction t=session.beginTransaction();
        User_Book user_book=new User_Book();
        user_book.setDate(new Date());
        user_book.setUserId("40289f0d51581b630151581b6d080000");
        user_book.setBookId("2bd8d910515d7e4101515d7e4f880000");
        session.save(user_book);
        t.commit();
        session.close();
    }
}
