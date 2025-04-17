package com.sky.mapper;

import com.sky.dto.AddHomeworkDTO;
import com.sky.entity.HomeWork;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HomeworkMapper {
    /**
     * 创建作业，逐条插入
     * @param addHomeworkDTO
     */
    @Insert("insert into homework(course_id,homework_name, homework_content, homework_url)" +
            " VALUES" +
            " (#{courseId}, #{homeworkName}, #{homeworkContent}, #{homeworkUrl})")
    void insert(AddHomeworkDTO addHomeworkDTO);

    /**
     * 查询课程全部作业
     * courseId
     */
    @Select("select * from homework where course_id = #{courseId} ")
    List<HomeWork> getAllHomework(Long courseId);



    /**
     * 删除课程单条作业
     * homeworkId
     */
    @Delete("delete from homework where homework_id = #{homeworkId} ")
    void deleteHomework(Long homeworkId);
}
