package com.sky.mapper;

import com.sky.dto.SubmitHomeworkDTO;
import com.sky.entity.User;
import com.sky.entity.UserHomework;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HomeworkStudentMapper {

    /**
     * 插入学生提交的作业
     * @param userHomework
     * @return
     */
    @Update("insert into homework_student (homework_id,student_id,homework_state,homework_score,homework_url,homework_content)" +
            "values"+
            "(#{homeworkId},#{studentId},#{homeworkState},#{homeworkScore},#{homeworkUrl},#{homeworkContent})")
    void insert(UserHomework userHomework);

    /**
     * 更新学生提交的作业,用于提交作业和评分
     *
     * @param
     */
    void update(UserHomework userHomework);

    /**
     * 查询学生完成情况,包括得分
     *
     * @param
     */
    @Select("select * from homework_student where homework_id = #{homeworkId} and student_id = #{studentId}" )
    UserHomework getHomeworkById(Long homeworkId,Long studentId);

    /**
     * 删除课程中所有所有学生作业
     *
     * @param
     */
    @Select("delete from homework_student where homework_id = #{homeworkId}")
    void deleteHomeworkByHomeworkId(Long homeworkId);

}
