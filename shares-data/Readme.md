### 练习3  得到股票数据
&emsp;&emsp;根据链接得到股票数据，多个线程同时运行获取股票数据，获取到一定量后再写入文件中(做这个的目的，主要是为了看看当获取到数据很多时候，应该怎么处理文件和用这个文件来做一些大数据查找相关题目的练习)。<br/><br/>
链接：[http://kanlon.ink:8081/shares-data/shares-data/](http://kanlon.ink:8081/shares-data/shares-data) <br/><br/>
&emsp;&emsp;当前功能较为简陋，有简陋日志记录错误信息，会输出记录到第几个了，大概10秒获取一次股票记录，然后每满10次后，就写入文件中。