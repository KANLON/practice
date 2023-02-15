#!/bin/bash
echo "xxl-job: hello shell"


# 启动cfile服务 
cd /data/services/cfile &&  nohup java -Dsys-service=cfile-8082 org.springframework.boot.loader.JarLauncher  &


# 启动datart 8081
cd /data/services/datart && /data/services/datart/bin/datart-server.sh start


# 启动zfile 8085   	
cd /data/services/zfile && /data/services/zfile/bin/start.sh


## 启动nsq 这里启动消息队列的时候，等前面启动后再启动其他

cd /data/services/nsq && nohup  /data/services/nsq/nsq-1.2.1.linux-amd64.go1.16.6/bin/nsqlookupd &
sleep 10s 
cd /data/services/nsq && nohup  /data/services/nsq/nsq-1.2.1.linux-amd64.go1.16.6/bin/nsqd --lookupd-tcp-address=127.0.0.1:4160 &
sleep 10s
cd /data/services/nsq && nohup  /data/services/nsq/nsq-1.2.1.linux-amd64.go1.16.6/bin/nsqadmin --lookupd-http-address=127.0.0.1:4161 &
sleep 10s


# 启动lepus 一定需要进入bin后启动，因为还会依赖这个来执行其他命令的

cd /data/services/lepus/lepus/bin && nohup ./lepus_proxy --config=../etc/proxy.ini &

cd /data/services/lepus/lepus/bin && nohup ./lepus_task --config=../etc/config.ini &

cd /data/services/lepus/lepus/bin && nohup ./lepus_alarm --config=../etc/alarm.ini &

ps -ef|grep lepus

echo "等待依赖启动后再启动控制台"
sleep 10s

## 启动lepus 控制台 8080
cd /data/services/lepus/lepus-console.5.1.linux-amd64 &&  nohup ./lepus_console &


# 启动xxl-job-executor 8083 9999
cd /data/services/xxl-job/xxl-job-admin/target && nohup java -Dsys-service=xxl-job-admin-8083 org.springframework.boot.loader.JarLauncher  &


# 启动xxl-job-executor 9083
cd /data/services/xxl-job/xxl-job-executor-samples/xxl-job-executor-sample-springboot/target && nohup java -Dsys-service=xxl-job--executor org.springframework.boot.loader.JarLauncher  &


# 启动superset 
sudo docker start my_superset
# 启动mysql
sudo docker start demo-mysql



echo "执行成功"
exit 0
