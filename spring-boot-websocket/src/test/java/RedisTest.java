import com.kanlon.App;
import com.kanlon.redis.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * redis操作测试类
 *
 * @author zhangcanlong
 * @since 2019/05/28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void saveReids() {
        User u = new User();
        u.setName("张三三");
        u.setId("1");
        redisTemplate.opsForValue().set(u.getId() + "", u);
        User result = (User) redisTemplate.opsForValue().get(u.getId() + "");
        System.out.println(result.toString());
    }

    @Test
    public void saveHashRedis() {
        for (int i = 1; i < 10; i++) {
            User u = new User();
            u.setName("王伟");
            u.setId(i + "");
            redisTemplate.opsForHash().put("myCache", u.getId(), u);
        }
        ArrayList<User> list = (ArrayList) redisTemplate.opsForHash().values("myCache");
        System.out.println(list);
    }
}
