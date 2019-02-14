# spring boot的自定义的Enable注解，@EnableSelfBean

该Enable注解可以将某些包下的所有类自动注册到spring容器中，对于一些实体类的项目很多的情况下，可以考虑一下通过这种方式将某包下所有类自动加入到spring容器，不再需要每个类再加上@Component等注解。<br/>

用法：<br/>

在spring boot启动类上加上@EnableSelfBean注解，并在packages值中写明你要自动加入所有类到spring的包。如下；<br/>

```
@SpringBootApplication
@EnableSelfBean(packages = {"com.kanlon.entity", "com.kanlon.utils"})
public class SpringBootEnableApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringBootEnableApplication.class, args);
    }
    
}
```