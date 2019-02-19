# 服务网关spring cloud zuul 初级，路由转发例子

注意事项；<br/>

1. Zuul通过URL配置路由转发中，zuul.routes.{serverid}.path，serverid是任意的，只要与zuul.routes.{serverid}.url中的serverid对应即可达到转发效果，另外将其注册到eureka服务注册中心后，会自动配置，serverid则是zuul服务地址后面的/{serverid}/，是服务注册中心中的spring.application.name