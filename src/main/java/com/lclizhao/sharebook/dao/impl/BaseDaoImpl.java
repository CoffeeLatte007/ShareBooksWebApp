package com.lclizhao.sharebook.dao.impl;/**
 * Created by lizhaoz on 2015/11/26.
 */

import com.lclizhao.sharebook.dao.BaseDao;
import com.lclizhao.utils.ClassUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
 class BaseDaoImpl<T>  implements BaseDao<T> {
    private final Logger logger= LogManager.getLogger(BaseDao.class.getName());
    Class entityClass = ClassUtil.getActualType(this.getClass());
    @Autowired
    private SessionFactory sessionFactory;
    /**
     * 获得当前事务的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public void save(T entity) {
        if(entity!=null){
            getCurrentSession().save(entity);
        }
    }

    @Override
    public void delete(T entity) {
        if (entity!=null){
            getCurrentSession().delete(entity);
        }
    }

    @Override
    public void update(T entity) {
        if (entity!=null){
            getCurrentSession().update(entity);
        }
    }

    @Override
    public T getById(Serializable id) {
        if(id!=null){
        return (T) getCurrentSession().get(entityClass,id);
        }
        return null;
    }

    @Override
    public void deleteObjectByIds(Serializable... ids) {
        if(ids!=null&&ids.length>0){
            for (Serializable id:ids){
                //先查询再删除
                Object entity=this.getById(id);
                this.getCurrentSession().delete(entity);
            }
        }
    }

    @Override
    public List<T> findCollectionByConditionNoPage(String condition, Object[] params, Map<String, String> orderby) {
        String hql="from"+entityClass.getSimpleName()+" o where 1=1";
        String orderbyCondition=this.orderbyHql(orderby);
        String finalhql=hql+condition+orderbyCondition;
        return getCurrentSession().createQuery(finalhql).list();
    }



    @Override
    public List<T> findCollectionByConditionPage(String condition, Object[] params, Map<String, String> orderby, int pageNo, int oageSize) {
        String hql="from"+entityClass.getSimpleName()+" o where 1=1";
        String orderbyCondition=this.orderbyHql(orderby);
        String finalhql=hql+condition+orderbyCondition;
        return getCurrentSession().createQuery(finalhql).setMaxResults(oageSize).setFirstResult((pageNo - 1) * oageSize).list();
    }
    /*
       顺序的拼接
     */
    private String orderbyHql(Map<String, String> orderby) {
        StringBuffer buffer= new StringBuffer("");
        if(orderby!=null&&orderby.size()>0){
            buffer.append("ORDER BY");
            for(Map.Entry<String,String> map:orderby.entrySet()){
                buffer.append(map.getKey()+" "+map.getValue()+",");
            }
            buffer.deleteCharAt(buffer.length()-1);
        }
        return  buffer.toString();

    }
}
