
# docker 练习

主要参考[docker官网文档](https://docs.docker.com/get-started/)和根据自己实践时的一些体会来记录下来的，以防忘记，一切以官网为准，官方文档最为详细。

### docker常用命令

1. `docker container ls` 或者 `docker ps`  列出运行的容器

2. `docker container stop` 暂停容器（widow系统下使用，linux下使用ctrl+c退出容器，就是暂停了）

3. `docker run -d -p 4000:80 friendlyhello`  以后台形式运行docker，-d  表示以后台形式   ， -p表示端口对应，  friendlyhello表示容器名

4. 上传镜像步骤：

   4.0 `docker login` 登陆公共仓库，之后输入用户和密码再进行登陆。如果要登陆私有仓库，则`docker login hostname`之后输入用户名或密码，或者`docker login -u 【用户名】 -p 【密码】 hostname`

   4.1 `docker tag images username/repository:tag`      【上传到中央仓库docker hub】images为你本地的镜像名，username为自己命名的仓库名，repository:tag为新的镜像名和对应标签。如果要上传到自己的私有仓库，需要这样命名：`docker tag images hostname/repository:tag`。hostname为对应私有仓库地址。
   
   4.2 `docker push username/repository:tag` 或 `docker push hostname/repository:tag`  上传
   
   
5. 集群服务配置Part 3: Services
   

   
6、	`docker exec -it container_name (或者 container_id)  /bin/bash` 登录、进入守护式容器
   
   
   
   
   
### 额外：

#### Part 2: Containers

```
docker build -t friendlyhello .  # Create image using this directory's Dockerfile
docker run -p 4000:80 friendlyhello  # Run "friendlyname" mapping port 4000 to 80
docker run -d -p 4000:80 friendlyhello         # Same thing, but in detached mode
docker container ls                                # List all running containers
docker container ls -a             # List all containers, even those not running
docker container stop <hash>           # Gracefully stop the specified container
docker container kill <hash>         # Force shutdown of the specified container
docker container rm <hash>        # Remove specified container from this machine
docker container rm $(docker container ls -a -q)         # Remove all containers
docker image ls -a                             # List all images on this machine
docker image rm <image id>            # Remove specified image from this machine
docker image rm $(docker image ls -a -q)   # Remove all images from this machine
docker login             # Log in this CLI session using your Docker credentials
docker tag <image> username/repository:tag  # Tag <image> for upload to registry
docker push username/repository:tag            # Upload tagged image to registry
docker run username/repository:tag                   # Run image from a registry

```

#### Part 3: services 

一个stack是一个分享依赖、相互协作的相关服务的集合。一个stack就足以定义和协调整个APP的功能。

```
docker stack ls                                            # List stacks or apps 列举栈服务或app服务
docker stack deploy -c <composefile> <appname>  # Run the specified Compose file 部署运行
docker service ls                 # List running services associated with an app
docker service ps <service>                  # List tasks associated with an app
docker inspect <task or container>                   # Inspect task or container
docker container ls -q                                      # List container IDs
docker stack rm <appname>                             # Tear down an application
docker swarm leave --force      # Take down a single node swarm from the manager
```

##### docker注意事项

1. 当在控制台输入过登陆了某个仓库，docker会在本主机安装对应该仓库的权限，当下次push的时候，会直接上传到对应仓库中。同一主机，可以存储多个仓库权限，只要登陆过，下次就不用登陆，直接上传可以成功。如果想要登出可以使用 docker logout 【仓库地址】。如果没有登陆过，则不能push成功。<br/><br/>
  `例如：`我有一个私有仓库叫做，docker.example.com:5000,用户名和密码都是admin，则我执行了docker login -u admin -p admin https://docker.example.com:5000 ,则之后即使关机再重启，我都可以直接push项目到该私有仓库。同时我再登陆docker hub，`docke login` 回车，然后输入用户名和密码，则下次即使关机再重启后，我也可以直接push到docker上。



















