package com.lclizhao.sharebook.daomain;/**
 * Created by lizhaoz on 2015/11/26.
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
public class BookTest {
    @Test
    public void save(){
        Session session=HibernateUtils.sessionFactory.openSession();
        Book book=new Book();
        book.setBookName("java编程思想");
        book.setiSBN("23217389217389");
        Transaction t=session.beginTransaction();
        session.save(book);
        t.commit();
        session.close();
    }
}
