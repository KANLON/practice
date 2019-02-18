# spring config git配置中心

注意事項:<br/>

1. 如果请求配置中心服务器长时间没有响应，有可能是你的github项目太大，建议把配置中心git单独放出来。（我就是犯了这个错误，找了很长时间）

2. spring config与spring-cloud相关的属性必须配置在bootstrap.properties中，config部分内容才能被正确加载。因为config的相关配置会先于application.properties，而bootstrap.properties的加载也是先于application.properties