/* quartz的相关的建表语句 *  */
USE test;

DROP TABLE IF EXISTS qrtz_fired_triggers;
DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
DROP TABLE IF EXISTS qrtz_scheduler_state;
DROP TABLE IF EXISTS qrtz_locks;
DROP TABLE IF EXISTS qrtz_simple_triggers;
DROP TABLE IF EXISTS qrtz_simprop_triggers;
DROP TABLE IF EXISTS qrtz_cron_triggers;
DROP TABLE IF EXISTS qrtz_blob_triggers;
DROP TABLE IF EXISTS qrtz_triggers;
DROP TABLE IF EXISTS qrtz_job_details;
DROP TABLE IF EXISTS qrtz_calendars;

/*创建自定义的任务表*/
DROP TABLE IF EXISTS tb_app_quartz;
CREATE TABLE tb_app_quartz
(
  quartz_id INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  job_name VARCHAR(100) NOT NULL COMMENT '任务名称',
  job_group VARCHAR(100) NOT NULL DEFAULT 'default' COMMENT '任务分组',
  start_time DATETIME NOT NULL DEFAULT NOW() COMMENT '任务开始时间',
  cron_expression VARCHAR(20) NOT NULL COMMENT 'corn表格式',
  invoke_param VARCHAR(255) NOT NULL COMMENT '需要传递的参数',
  invoke_param2 VARCHAR(255) COMMENT '需要传递的参数2',
  charge VARCHAR(255) NOT NULL COMMENT '负责人姓名',
  charge_department VARCHAR(255) NOT NULL COMMENT '负责人部门',
  -- 由于不能同时创建两个default timestamp默认值所以将创建时间的默认值修改为'1991-01-01 00:00:00'
  ctime TIMESTAMP NOT NULL DEFAULT '1991-01-01 00:00:00' COMMENT '创建时间',
  mtime TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '修改时间',
  dr TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否有效,标记删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='自定义的任务表';

/**创建任务执行日志结果表*/
DROP TABLE IF EXISTS tb_quartz_result;
CREATE TABLE tb_quartz_result
(
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  quartz_id INT(11) NOT NULL  COMMENT '任务id',
  start_time DATETIME NOT NULL COMMENT '调度开始时间',
  schedule_result TINYINT(1) NOT NULL COMMENT '调度结果。0,表示失败，1表示成功。2表示执行中',
  exec_result TINYINT(1) NOT NULL COMMENT '执行结果。0,表示失败，1表示成功。2表示执行中',
  exec_time INT(11) NOT NULL COMMENT '执行时间，毫秒',
  complete_time DATETIME NOT NULL COMMENT '调度/执行完成时间',
  remark VARCHAR(65535) COMMENT '备注',
  ctime TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '创建时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='任务执行日志结果表';
-- 创建索引
ALTER TABLE tb_quartz_result ADD INDEX quartz_id_index (quartz_id);

CREATE TABLE qrtz_job_details
  (
    sched_name VARCHAR(120) NOT NULL,
    job_name  VARCHAR(200) NOT NULL,
    job_group VARCHAR(200) NOT NULL,
    description VARCHAR(250) NULL,
    job_class_name   VARCHAR(250) NOT NULL,
    is_durable VARCHAR(1) NOT NULL,
    is_nonconcurrent VARCHAR(1) NOT NULL,
    is_update_data VARCHAR(1) NOT NULL,
    requests_recovery VARCHAR(1) NOT NULL,
    job_data BLOB NULL,
    PRIMARY KEY (sched_name,job_name,job_group)
) DEFAULT CHARSET=utf8 COMMENT='存储每一个已配置的 Job 的详细信息(jobDetail)';

CREATE TABLE qrtz_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    job_name  VARCHAR(200) NOT NULL,
    job_group VARCHAR(200) NOT NULL,
    description VARCHAR(250) NULL,
    next_fire_time BIGINT(13) NULL,
    prev_fire_time BIGINT(13) NULL,
    priority INTEGER NULL,
    trigger_state VARCHAR(16) NOT NULL,
    trigger_type VARCHAR(8) NOT NULL,
    start_time BIGINT(13) NOT NULL,
    end_time BIGINT(13) NULL,
    calendar_name VARCHAR(200) NULL,
    misfire_instr SMALLINT(2) NULL,
    job_data BLOB NULL,
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,job_name,job_group)
          REFERENCES qrtz_job_details(sched_name,job_name,job_group)
) DEFAULT CHARSET=utf8  COMMENT='存储已配置的 触发器 (Trigger) 的信息';

CREATE TABLE qrtz_simple_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    repeat_count BIGINT(7) NOT NULL,
    repeat_interval BIGINT(12) NOT NULL,
    times_triggered BIGINT(10) NOT NULL,
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,trigger_name,trigger_group)
        REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
)  DEFAULT CHARSET=utf8 COMMENT='存储简单的 Trigger，包括重复次数，间隔，以及已触的次数';

CREATE TABLE qrtz_cron_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    cron_expression VARCHAR(200) NOT NULL,
    time_zone_id VARCHAR(80),
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,trigger_name,trigger_group)
        REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
)  DEFAULT CHARSET=utf8 COMMENT='存储 Cron Trigger，包括 Cron 表达式和时区信息';

CREATE TABLE qrtz_simprop_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    str_prop_1 VARCHAR(512) NULL,
    str_prop_2 VARCHAR(512) NULL,
    str_prop_3 VARCHAR(512) NULL,
    int_prop_1 INT NULL,
    int_prop_2 INT NULL,
    long_prop_1 BIGINT NULL,
    long_prop_2 BIGINT NULL,
    dec_prop_1 NUMERIC(13,4) NULL,
    dec_prop_2 NUMERIC(13,4) NULL,
    bool_prop_1 VARCHAR(1) NULL,
    bool_prop_2 VARCHAR(1) NULL,
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,trigger_name,trigger_group)
    REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
) DEFAULT CHARSET=utf8 COMMENT='';

CREATE TABLE qrtz_blob_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    blob_data BLOB NULL,
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,trigger_name,trigger_group)
        REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
)  DEFAULT CHARSET=utf8 COMMENT='以 Blob 类型存储的Trigger';

CREATE TABLE qrtz_calendars
  (
    sched_name VARCHAR(120) NOT NULL,
    calendar_name  VARCHAR(200) NOT NULL,
    calendar BLOB NOT NULL,
    PRIMARY KEY (sched_name,calendar_name)
) DEFAULT CHARSET=utf8  COMMENT='以 Blob 类型存储 Quartz 的 Calendar 信息';

CREATE TABLE qrtz_paused_trigger_grps
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_group  VARCHAR(200) NOT NULL,
    PRIMARY KEY (sched_name,trigger_group)
)  DEFAULT CHARSET=utf8 COMMENT='存储已暂停的 Trigger 组的信息';

CREATE TABLE qrtz_fired_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    entry_id VARCHAR(95) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    instance_name VARCHAR(200) NOT NULL,
    fired_time BIGINT(13) NOT NULL,
    sched_time BIGINT(13) NOT NULL,
    priority INTEGER NOT NULL,
    state VARCHAR(16) NOT NULL,
    job_name VARCHAR(200) NULL,
    job_group VARCHAR(200) NULL,
    is_nonconcurrent VARCHAR(1) NULL,
    requests_recovery VARCHAR(1) NULL,
    PRIMARY KEY (sched_name,entry_id)
)  DEFAULT CHARSET=utf8 COMMENT='存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息';

CREATE TABLE qrtz_scheduler_state
  (
    sched_name VARCHAR(120) NOT NULL,
    instance_name VARCHAR(200) NOT NULL,
    last_checkin_time BIGINT(13) NOT NULL,
    checkin_interval BIGINT(13) NOT NULL,
    PRIMARY KEY (sched_name,instance_name)
)  DEFAULT CHARSET=utf8 COMMENT='存储少量的有关调度器 (Scheduler) 的状态，和别的 调度器 (Scheduler)实例(假如是用于一个集群中)';

CREATE TABLE qrtz_locks
  (
    sched_name VARCHAR(120) NOT NULL,
    lock_name  VARCHAR(40) NOT NULL,
    PRIMARY KEY (sched_name,lock_name)
) DEFAULT CHARSET=utf8  COMMENT='存储程序的非观锁的信息(假如使用了悲观锁)';


COMMIT;



-- 查询所有任务
SELECT
  tb.`quartz_id`,tb.`job_name`, tb.`job_group`, tb.`invoke_param`, tb.`mtime`, tb.`start_time`, tb.`cron_expression`,
  tb.`charge`,tb.`charge_department`,tb.`invoke_param2`,
  qrtz.`description`, qrtz.`next_fire_time`, qrtz.`trigger_state`
FROM
  tb_app_quartz tb
  LEFT JOIN qrtz_triggers qrtz
    ON tb.`job_name` = qrtz.`job_name`
    AND tb.`job_group` = qrtz.`job_group`
WHERE tb.`dr` = 0
ORDER BY tb.mtime DESC
LIMIT 0, 100

