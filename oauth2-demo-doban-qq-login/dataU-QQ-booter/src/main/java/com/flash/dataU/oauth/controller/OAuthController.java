package com.flash.dataU.oauth.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author sunyiming (sunyiming170619@credithc.com)
 * @version 0.0.1-SNAPSHOT
 * @since 2017年08月23日 16时13分
 */
@Controller
public class OAuthController {

    //暂时先固定code为全局遍历，实际中要将其放到session域或者nosql中
    private static  String globalCode = "";

    private static String getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
        //return "10.100.19.211";
    }

    private static String getRedirect_uri() throws UnknownHostException {
        return "http://"+ getLocalHost() +":7001/index";
    }

    /**
     * （A）用户访问客户端，后者将前者导向认证服务器，即跳转到登录页面
     */
    @RequestMapping("authorize")
    public String authorize(String response_type, String client_id, String redirect_uri, String scope, String state){

        return "login";
    }

    /**
     * （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。极简易的登录，先把用户名和密码放到链接后面
     */
    @RequestMapping("login")//
    public void login(@PathParam("username") String username, @PathParam("password") String password, HttpServletRequest request,HttpServletResponse response) throws IOException {

        // 验证用户名密码是否正确
        if("admin".equals(username) && "admin".equals(password)){
            globalCode=new StringBuilder(username+password).reverse().toString();
            response.sendRedirect(getRedirect_uri() + "?state=hehe&code="+globalCode);
        }else{
            response.sendRedirect("http://"+ getLocalHost() +":7000/authorize?" +
                    "response_type=code&" +
                    "client_id=10000032&" +
                    "redirect_uri=http://"+ getLocalHost() +":7001/index&" +
                    "scope=userinfo&" +
                    "state=hehe");
        }


    }

    /**
     * （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
     */
    @RequestMapping("getTokenByCode")
    @ResponseBody
    public String getTokenByCode(String grant_type, String code, String client_id, String redirect_uri) throws IOException {

        // 判断client_id、redirect_uri、code是否正确
        // 返回token
        if(globalCode.equals(code)){
            return "{access_token:yyy," +
                    "token_type:bearer," +
                    "expires_in:600," +
                    "refresh_token:zzz}";
        }else{
            return "{access:false}";
        }

    }

    /**
     * （F）资源服务器确认令牌无误，同意向客户端开放资源。。
     */
    @RequestMapping("getUserinfoByToken")
    @ResponseBody
    public String getUserinfoByToken(String token) throws IOException {
        // 判断token是否正确
        return "{userGuid:j3jlk2jj32li43i," +
            "username:Tom," +
            "mobile:18811412324}";
    }

}
