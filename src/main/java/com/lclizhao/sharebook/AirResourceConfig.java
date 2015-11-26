package com.lclizhao.sharebook;/**
 * Created by lizhaoz on 2015/11/26.
 */

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:资源类的启动入口
 */
@ApplicationPath("/webapi/*")
public class AirResourceConfig extends ResourceConfig {
    public AirResourceConfig() {
        packages("com.lclizhao.resouce");
        /**register(BookResource.class);**/
    }
}
