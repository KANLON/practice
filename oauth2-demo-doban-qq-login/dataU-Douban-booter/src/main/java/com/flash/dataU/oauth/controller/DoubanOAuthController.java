package com.flash.dataU.oauth.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * .
 *
 * @author sunyiming (sunyiming170619@credithc.com)
 * @version 0.0.1-SNAPSHOT
 * @since 2017年08月23日 16时39分
 */
@Controller
public class DoubanOAuthController {

    private static String getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
        //return "10.100.19.211";
    }

    @RequestMapping("leadToAuthorize")
    public void leadToAuthorize(HttpServletResponse response) throws Exception {
        response.sendRedirect("http://"+ getLocalHost() +":7000/authorize?" +
            "response_type=code&" +
            "client_id=10000032&" +
            "redirect_uri=http://"+ getLocalHost() +":7001/index&" +
            "scope=userinfo&" +
            "state=hehe");
    }

    @RequestMapping("index")
    public String index(String code, HttpServletRequest request) throws Exception {

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        /**
         * （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
         */
        String accessToken = restTemplate.getForObject("http://"+ getLocalHost() +":7000/getTokenByCode?" +
            "grant_type=authorization_code&" +
            "code=" +code+
            "&redirect_uri=http://"+ getLocalHost() +":7001/index", String.class);

        //如果获取不到token，返回登录页重试
        if(accessToken.equals("{access:false}")){
            return "login.html";
        }

        /**
         * 发起通过token换用户信息的请求
         */
        String username = restTemplate.getForObject("http://"+ getLocalHost() +":7000/getUserinfoByToken?" +
            "access_token=yyy", String.class);

        request.getSession().setAttribute("username",username);

        return "index";
    }


    @RequestMapping("getUserInfo")
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws Exception {
        Object username = request.getSession().getAttribute("username");
        return "Tom 18811311416";
    }

}
