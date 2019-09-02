package com.kanlon;


import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.baomidou.mybatisplus.annotation.DbType;
import org.junit.Test;

/**
 * 其他测试
 *
 * @author zhangcanlong
 * @since 2019/9/2 17:33
 **/
public class OtherTest {

    @Test
    public void testSql(){
        String sql = " SELECT * FROM ( SELECT g.dt, g.country, g.hdid_cnt AS global_hdid_cnt, g.global_avg_keep1_hdid_cnt, g.global_avg_keep7_hdid_cnt, g.global_avg_keep30_hdid_cnt, n.hdid_cnt AS new_hdid_cnt, n.new_avg_keep1_hdid_cnt AS new_avg_keep1_hdid_cnt, n.new_avg_keep7_hdid_cnt AS new_avg_keep7_hdid_cnt, n.new_avg_keep30_hdid_cnt AS new_avg_keep30_hdid_cnt, video.pct AS video_pct, video.avg_play_time AS video_avg_play_time, video.avg_play_cnt AS video_avg_play_cnt FROM (SELECT dt, country, hdid_cnt, CASE WHEN keep1_hdid_cnt=0 THEN NULL ELSE ROUND(keep1_hdid_cnt/hdid_cnt,4) END AS global_avg_keep1_hdid_cnt, CASE WHEN keep7_hdid_cnt=0 THEN NULL ELSE ROUND(keep7_hdid_cnt/hdid_cnt,4) END AS global_avg_keep7_hdid_cnt, CASE WHEN keep30_hdid_cnt=0 THEN NULL ELSE ROUND(keep30_hdid_cnt/hdid_cnt,4) END AS global_avg_keep30_hdid_cnt FROM dm_zhuikan_core_data_d WHERE layer_id = 'all' AND test_id = 'all' AND push_type = 'all' AND user_type = 'all' AND event_type = 'all' AND ver = 'all' AND sys = 'all') g INNER JOIN (SELECT dt, country, hdid_cnt, CASE WHEN keep1_hdid_cnt=0 THEN NULL ELSE ROUND(keep1_hdid_cnt/hdid_cnt,4) END AS new_avg_keep1_hdid_cnt, CASE WHEN keep7_hdid_cnt=0 THEN NULL ELSE ROUND(keep7_hdid_cnt/hdid_cnt,4) END AS new_avg_keep7_hdid_cnt, CASE WHEN keep30_hdid_cnt=0 THEN NULL ELSE ROUND(keep30_hdid_cnt/hdid_cnt,4) END AS new_avg_keep30_hdid_cnt FROM dm_zhuikan_core_data_d WHERE layer_id = 'all' AND test_id = 'all' AND push_type = 'all' AND user_type = '新' AND event_type = 'all' AND ver = 'all' AND sys = 'all') n ON g.dt = n.dt AND g.country = n.country INNER JOIN (SELECT dt, country, pct, ROUND(play_cnt / hdid_cnt, 2) AS avg_play_cnt, ROUND(play_time / hdid_cnt, 2) AS avg_play_time FROM dm_zhuikan_core_data_d WHERE layer_id = 'all' AND test_id = 'all' AND push_type = 'all' AND user_type = 'all' AND event_type = '视频消费' AND ver = 'all' AND sys = 'all') video ON n.dt = video.dt AND n.country = video.country ) a where dt<='2019-09-01' and dt>='2001-01-01' and country='all' ORDER BY dt desc ";
        System.out.println(SQLUtils.addCondition(sql,"a=2", null));
        SQLSelectOrderByItem sqlSelectOrderByItem = SQLUtils.toOrderByItem(sql,null);
    }

}
