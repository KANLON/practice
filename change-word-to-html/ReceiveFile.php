
<?php
/**
 * Created by PhpStorm.
 * User: zhangcanlong
 * Date: 2016/11/15
 * Time: 23:41
 */
function word2html($wordname,$htmlname)
{
    //获取链接地址
     /*
    //$url=$_SERVER['HTTP_HOST'];//获取服务器地址
    // $url=$url.$_SERVER['PHP_SELF'];//获取当前服务器下的文件名和目录
    // $url=dirname($url)."/";*/        //去除目录中的文件名
    $word = new COM("word.application") or die("找不到 Word 程序"); // 建立一个指向新COM组件的索引
    // 显示目前正在使用的Word的版本号
    echo "Loading Word, v. {$word->Version}<br>";
    // 把它的可见性设置为0（假），如果要使它在最前端打开，使用1（真） 
    $word->Visible = 0;

     $word->Documents->Open($wordname) or die("无法打开这文件");

 header("Content-Type: text/html;charset=utf-8");//设置文件的格式
    //打开一个文档
 //把文档保存在目录中  
try{

$word->Documents[1]->SaveAs($htmlname,8);
} catch(Exception $e){
    print $e->getMessage();
}
    $content=file_get_contents($htmlname);
    echo $content;//输出word文档的内容
// 关闭与COM组件之间的连接
$word->Quit();
 unset($word);   
 }

//获取当前文件下的目录
$file_Name=dirname(__FILE__);
echo $file_Name;
word2html("$file_Name\\2.docx","$file_Name\\2.html");//要转换的word文件和转换成的html的文件名
?>