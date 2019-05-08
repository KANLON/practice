
-- 创建MyISAM模式表方便批量跑数据
 
CREATE TABLE `logs_info1` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `dt` VARCHAR(20) NOT NULL,
  `logtype` VARCHAR(255) DEFAULT NULL,
  `logurl` VARCHAR(255) DEFAULT NULL,
  `logip` VARCHAR(255) DEFAULT NULL,
  `logdz` VARCHAR(255) DEFAULT NULL,
  `ladduser` VARCHAR(255) DEFAULT NULL,
  `lfadduser` VARCHAR(255) DEFAULT NULL,
  `laddtime` DATETIME DEFAULT NULL,
  `htmlname` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MYISAM  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='日志表';
-- 创建索引
CREATE INDEX dtIndex ON cfile.logs_info(dt);
-- 添加表字段
ALTER TABLE cfile.logs_info ADD (ctime  DATE DEFAULT '1991-01-01 10:10:10' NOT NULL);
ALTER TABLE cfile.logs_info ADD ( mtime DATETIME DEFAULT NOW() NOT NULL);
 

-- 创建存储过程

DROP PROCEDURE IF EXISTS my_insert;


DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    PROCEDURE `cfile`.`my_insert`()
    /*LANGUAGE SQL
    | [NOT] DETERMINISTIC
    | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
    | SQL SECURITY { DEFINER | INVOKER }
    | COMMENT 'string'*/
	BEGIN
	
   DECLARE n INT DEFAULT 1;
   DECLARE i INT DEFAULT 1;
        loopname:LOOP
            INSERT INTO `logs_info`(`dt`,`logtype`,`logurl`,`logip`,`logdz`,`ladduser` ,`lfadduser`,`laddtime`,`htmlname`) VALUES ( CONCAT('2018-06-0',i),2+n, '/index', '0:0:0:0:0:0:0:1', n, n+1, 'null', '2018-05-03 14:02:42', '首页');
            SET n=n+1;
	    IF n%300000=0 THEN 
	        SET i=i+1; 
            END IF;
        IF n=3000000 THEN
            LEAVE loopname;
        END IF;
        END LOOP loopname;

	END$$

DELIMITER ;
 
 
-- 执行存储过程
CALL my_insert();
 
 
-- 数据插入成功后修改表模式InnoDB 时间稍微久点
 ALTER TABLE `logs_info` ENGINE=INNODB;
 
 
 
-- 方法一，使用in
EXPLAIN
SELECT d.id FROM logs_info d  WHERE htmlname='第一页' AND logurl='/one' AND dt IN('2018-05-01','2018-05-02','2018-05-04','2018-05-08');
3.4

-- 方法二，使用or
EXPLAIN 
SELECT d.id FROM logs_info d  WHERE htmlname='第一页' AND logurl='/one' AND (dt ='2018-05-01' OR dt='2018-05-02' OR dt='2018-05-04' OR dt='2018-05-08');
3.4

-- 方法三 使用union all
EXPLAIN
SELECT d.id FROM logs_info d WHERE d.htmlname='第一页' AND d.logurl='/one' AND d.dt='2018-05-01'
UNION ALL
SELECT d.id FROM logs_info d WHERE d.htmlname='第一页' AND d.logurl='/one' AND d.dt='2018-05-02'
UNION ALL
SELECT d.id FROM logs_info d WHERE d.htmlname='第一页' AND d.logurl='/one' AND d.dt='2018-05-04'
UNION ALL
SELECT d.id FROM logs_info d WHERE d.htmlname='第一页' AND d.logurl='/one' AND d.dt='2018-05-08'

-- 方法四，代码解决，多线程单个执行
3秒左右

EXPLAIN 

SELECT d.id FROM logs_info d  WHERE htmlname='第一页' AND logurl='/one' AND dt =('2018-05-01');

