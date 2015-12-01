package com.lclizhao.sharebook.daomain;/**
 * Created by lizhaoz on 2015/11/29.
 */


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 */
@Entity
@Table(name = "user_book")
public class User_Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "ubId",unique = true,nullable = false,length = 32)
    private String ubId;



}
