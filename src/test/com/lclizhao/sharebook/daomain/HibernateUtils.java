package com.lclizhao.sharebook.daomain;/**
 * Created by lizhaoz on 2015/11/26.
 */

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
public class HibernateUtils {
    public static SessionFactory sessionFactory;
    static {
        Configuration configuration=new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder serviceRegistryBuilder  = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        StandardServiceRegistry serviceRegisty=serviceRegistryBuilder.build();
         sessionFactory=configuration.buildSessionFactory(serviceRegisty);
    }
}
