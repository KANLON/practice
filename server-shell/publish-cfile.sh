#!/bin/bash
echo "开始发版cfile"

echo "创建授权cfile的日志目录和文件上传目录"
sudo mkdir -vp /opt/cfile/upload
sudo chmod  777 /opt/cfile
sudo chmod  777 /opt/cfile/upload

cd /data/services/cfile

echo "停止历史cfile程序"
ps -ef | grep java | grep cfile
cfilePid=`ps -ef | grep java | grep cfile | awk '{print $2}'`
kill -9  ${cfilePid}

echo "停止cfile结果如下（ps -ef | grep java | grep cfile）："
ps -ef | grep java | grep cfile

echo "清空历史文件"
echo "" > /data/services/cfile/nohup.out
rm -rf /data/services/cfile/BOOT-INF
rm -rf /data/services/cfile/META-INF
rm -rf /data/services/cfile/org

echo "解压新的cfile程序文件"
cd /data/services/cfile
unzip /data/services/cfile/cfile-0.0.3-SNAPSHOT.jar


echo "复制配置文件"
/bin/cp -rf  /data/services/cfile/application.properties.bak20221008  BOOT-INF/classes/application.properties

echo "启动cfile java程序"
# 2>&1 解释：将标准错误 2 重定向到标准输出 &1，标准输出 &1 再被重定向到 /data/services/cfile/nohup.out 文件中，通常用来后台启动服务，然后输出log方便进行debug回溯。
nohup java -Dsys-service=cfile org.springframework.boot.loader.JarLauncher   > /data/services/cfile/nohup.out 2>&1  &

echo "暂停10秒"
sleep 10

echo "启动cfile的日志为："

cat /data/services/cfile/nohup.out

echo "脚本位置：$0"
echo "任务参数：$1"
echo "分片序号 = $2"
echo "分片总数 = $3"


exit 0
