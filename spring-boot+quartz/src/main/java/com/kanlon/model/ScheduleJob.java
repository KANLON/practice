package com.kanlon.model;

/**
 * 返回的任务调度信息实体类
 *
 * @author zhangcanlong
 * @since 2019/4/12 10:30
 **/
public class ScheduleJob {

        /**触发器名字*/
        private String name;
        /**任务组名字*/
        private String group;
        /**描述*/
        private String description;
        /**cron表达式*/
        private String cron;
        /**状态*/
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ScheduleJob{" + "name='" + name + '\'' + ", group='" + group + '\'' + ", description='" + description + '\'' + ", cron='" + cron + '\'' + ", status='" + status + '\'' + '}';
        }
}
