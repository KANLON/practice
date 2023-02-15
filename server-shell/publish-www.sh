#!/bin/bash
echo "开始发版www首页,当前文件状态为："
# 首页部署的目录地址
www_dir=/data/services/www
cd ${www_dir}
ls -lt

echo "开始clone并复制最新的首页文件"
# :- 设置默认值，以防不存在的时候，删除错误文件
rm -rf ${www_dir:-/tmp}/www
git clone https://github.com/KANLON/www.git
/bin/cp -rf ${www_dir}/www/* ${www_dir}/
echo "复制完成,当前目录文件为："
ls -lt

echo "开始安装依赖和编译yarn install 和 yarn build"
yarn install
yarn build

echo "发版首页成功"
curl -i http://www.kanlon.ink

echo "脚本位置：$0"
echo "任务参数：$1"
echo "分片序号 = $2"
echo "分片总数 = $3"


exit 0
