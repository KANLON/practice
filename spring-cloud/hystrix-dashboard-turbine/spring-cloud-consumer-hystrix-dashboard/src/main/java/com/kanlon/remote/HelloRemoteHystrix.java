package com.kanlon.remote;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用错误的回调类
 *
 * @author zhangcanlong
 * @since 2019/2/13 20:21
 **/
@Component("helloRemoteHystrix")
public class HelloRemoteHystrix implements HelloRemote {

    @Override
    public String index(@RequestParam(value="name") String name) {
        return "hello "+name+",this message send failed";
    }
}
