package com.lclizhao.sharebook.service.impl;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.dao.CollectionDao;
import com.lclizhao.sharebook.daomain.User_Book;
import com.lclizhao.sharebook.resource.Exception.AppException;
import com.lclizhao.sharebook.service.CollectionService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Name:BookServiceImpl
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-30（创建日期）
 * @Description:
 * 收集书的Service
 */
@Service("collectionService")
//设置统一的只读事务,具体的在下面细粒度配置
@Transactional(readOnly = true)
public class CollectionServiceImpl implements CollectionService {
    private final Logger logger= LogManager.getLogger(CollectionServiceImpl.class.getName());
    @Autowired
    CollectionDao collectionDao;

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = false)
    public User_Book save(User_Book user_book) throws AppException {
        String condition="";
        //参数列表
        List<Object> paramsList = new ArrayList<Object>();
        if(StringUtils.isNotBlank(user_book.getBookId())){
            condition += " and o.bookId=? ";
            paramsList.add(user_book.getBookId());
        }
        if(StringUtils.isNotBlank(user_book.getUserId())){
            condition += " and o.userId=? ";
            paramsList.add(user_book.getUserId());
        }

        List<User_Book> list =collectionDao.findCollectionByConditionNoPage(condition, paramsList.toArray(), null);
        if (list!=null&&list.size()>0){
            String message="collection_exist(try PUT if you want to update)";
            logger.error(message);
            throw new AppException(409,2005,"link",message,message);
        }
        user_book.setDate(new Date());
        collectionDao.save(user_book);
        return user_book;
    }

    @Override
    public User_Book getById(String ubId) {
       return collectionDao.getById(ubId);
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = false)
    @Override
    public User_Book update(User_Book user_book) throws  AppException{
        String condition="";
        //参数列表
        List<Object> paramsList = new ArrayList<Object>();
        if(StringUtils.isNotBlank(user_book.getBookId())){
            condition += " and o.bookId=? ";
            paramsList.add(user_book.getBookId());
        }
        if(StringUtils.isNotBlank(user_book.getUserId())){
            condition += " and o.userId=? ";
            paramsList.add(user_book.getUserId());
        }

        List<User_Book> list =collectionDao.findCollectionByConditionNoPage(condition, paramsList.toArray(), null);
        if (list!=null&&list.size()>0){
//            user_book.setUbId(list.get(0).getUbId());
//            collectionDao.update(user_book);
            User_Book getub=list.get(0);

                //事务结束自动提交，不需要再额外增加update方法
                getub.setStatus(user_book.getStatus());
                getub.setPrivcy(user_book.getPrivcy());
                getub.setRating(user_book.getRating());
                getub.setComment(user_book.getComment());

            return getub;
        }
        String message="collection_dont_exist";
        logger.error(message);
        throw new AppException(404,2011,"link",message,message);

    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = false)
    @Override
    public void delete(String bookId, String userId) throws AppException{
        String condition="";
        //参数列表
        List<Object> paramsList = new ArrayList<Object>();
        if(StringUtils.isNotBlank(bookId)){
            condition += " and o.bookId=? ";
            paramsList.add(bookId);
        }
        if(StringUtils.isNotBlank(userId)){
            condition += " and o.userId=? ";
            paramsList.add(userId);
        }

        List<User_Book> list =collectionDao.findCollectionByConditionNoPage(condition, paramsList.toArray(), null);
        if (list!=null&&list.size()>0){
            collectionDao.delete(list.get(0));
        }
        else {
            String message="collection_dont_exist";
            logger.error(message);
            throw new AppException(404,2011,"link",message,message);
        }
    }
}
