package com.lclizhao.sharebook.resource;/**
 * Created by lizhaoz on 2015/11/30.
 */

import com.lclizhao.sharebook.daomain.Token;
import com.lclizhao.sharebook.daomain.User;
import com.lclizhao.sharebook.service.UserService;
import com.lclizhao.utils.KeyUtil;
import com.lclizhao.utils.TokenUtil;
import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;


/**
 * @Name:
 * @Author: lizhao（作者）
 * @Version: V1.00 （版本号）
 * @Create Date: 2015-11-26（创建日期）
 * @Description:
 * 验证资源类
 */
@PermitAll()
@Path("/authentication")
public class AuthenticationResource {
    private final static Logger logger = LogManager.getLogger(AuthenticationResource.class.getName());

    @Autowired
    UserService userService;
    @Context
    ServletContext context;
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@NotNull @FormParam("telphone") String username,
                                     @NotNull @FormParam("password") String password) {
        Date expiry = getExpiryDate(30*24*60);//15分钟
        User user = authenticate(username, password);

        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        String jwtString = TokenUtil.getJWTString(username, expiry, KeyUtil.getKey(context));
        Token token = new Token();
        token.setAuthToken(jwtString);
        token.setExpires(expiry);

        return Response.ok(token).build();
    }
    private Date getExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
    private User authenticate(String username, String password) throws NotAuthorizedException {

        User user = null;

            user = userService.findUserByTel(username);
            if(user==null) {
                logger.info("Invalid username '" + username + "' ");
                throw new NotAuthorizedException("Invalid telpone '" + username + "' ");
            }
        // we need to actually test the Hash not the password, we should never store the password in the database.
        if (user.getPassword().equals(password)) {
            logger.info("USER AUTHENTICATED");
        } else {
            logger.info("USER NOT AUTHENTICATED");
            throw new NotAuthorizedException("Invalid username or password");
        }
        return user;
    }
}
