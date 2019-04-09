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
);

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
);

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
);

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
);

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
);

CREATE TABLE qrtz_blob_triggers
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    blob_data BLOB NULL,
    PRIMARY KEY (sched_name,trigger_name,trigger_group),
    FOREIGN KEY (sched_name,trigger_name,trigger_group)
        REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
);

CREATE TABLE qrtz_calendars
  (
    sched_name VARCHAR(120) NOT NULL,
    calendar_name  VARCHAR(200) NOT NULL,
    calendar BLOB NOT NULL,
    PRIMARY KEY (sched_name,calendar_name)
);

CREATE TABLE qrtz_paused_trigger_grps
  (
    sched_name VARCHAR(120) NOT NULL,
    trigger_group  VARCHAR(200) NOT NULL,
    PRIMARY KEY (sched_name,trigger_group)
);

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
);

CREATE TABLE qrtz_scheduler_state
  (
    sched_name VARCHAR(120) NOT NULL,
    instance_name VARCHAR(200) NOT NULL,
    last_checkin_time BIGINT(13) NOT NULL,
    checkin_interval BIGINT(13) NOT NULL,
    PRIMARY KEY (sched_name,instance_name)
);

CREATE TABLE qrtz_locks
  (
    sched_name VARCHAR(120) NOT NULL,
    lock_name  VARCHAR(40) NOT NULL,
    PRIMARY KEY (sched_name,lock_name)
);


COMMIT;
