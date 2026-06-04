package com.bbjcz.edu.mapper;

import com.bbjcz.edu.entity.ScoreUpdateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select id, student_id, class_id, new_score, update_time from score_update_log")
    List<ScoreUpdateLog> getAllLogs();
}
