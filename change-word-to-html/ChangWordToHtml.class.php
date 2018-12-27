<?php

/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2016/12/6
 * Time: 20:06
 */
//将word转化为html的类实例化该类，调用word2html函数能将word转变为html文件并保存在网页当前路径上一层文件夹中的upload文件夹中，
//调用GetHtmlContentByHtmlId，能得到相应html文件的内容
class ChangWordToHtml
{
//将word转为html,并且返回word的内容,文件名用id或实际文件名的转码不能用中文，我们要预先帮他们转码
    function word2html($wordName, $htmlName)
    {
        define("UPLOADFILE_PATH", $_SERVER['DOCUMENT_ROOT'] . "\\SAU-IMS-PHP\\upload\\");//!!!如果要修改存放路径，改这里，要转化的word文档的文件名和转成的html的文件名“绝对路径”
        $wordPath = UPLOADFILE_PATH . $wordName;
        $htmlPath = UPLOADFILE_PATH . $htmlName;
        $word = new COM("word.application") or die("找不到 Word 程序");              // 建立一个指向新COM组件的索引
        $word->Visible = 0;                                                                // 把它的可见性设置为0（假），如果要使它在最前端打开，使用1（真）
        try {
            $word->Documents->Open($wordPath) or die("无法打开这文件");
        } catch (Exception $e) {
            echo($e);
        }
        header("Content-Type: text/html;charset=utf-8");                            //设置文件的格式
        try {
            $word->Documents[1]->SaveAs($htmlPath, 8);                                       //把文档保存在目录中
        } catch (Exception $e) {
            print $e->getMessage();
        }
        $word->Quit();// 关闭与COM组件之间的连接
        unset($word);

    }

    function GetHtmlContentByHtmlName($htmlName)
    {
        $content = file_get_contents(UPLOADFILE_PATH . "$htmlName");
        $wordValue = @iconv("gb2312", "utf-8//IGNORE", $content);//使用@抵制错误，如果转换字符串中，某一个字符在目标字符集里没有对应字符，
        //那么，这个字符之后的部分就被忽略掉了；即结果字符串内容不完整，此时要使用//IGNORE
        return $wordValue;
    }

}
//test
$testWTH=new ChangWordToHtml();
$testWTH->word2html("1.docx","1.html");
echo $testWTH->GetHtmlContentByHtmlName("1.html");
