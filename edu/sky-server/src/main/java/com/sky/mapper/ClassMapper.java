package com.sky.mapper;

import com.sky.dto.UpdateClassDTO;
import com.sky.entity.Course;
import com.sky.entity.UserHomework;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {
    /**
     * 创建课程的课表，逐条插入
     * @param aclass
     */
    @Insert("insert into class(class_name,course_id, video_url, class_content)" +
            " VALUES" +
            " (#{className}, #{courseId}, #{videoUrl}, #{classContent})")
    void insert(com.sky.entity.Class aclass);

    /**
     * 查询课程课表
     * courseId
     */
    @Select("select * from class where course_id = #{courseId} ")
    List<com.sky.entity.Class> selectAllClasses(Long courseId);

    /**
     * 更新课表信息
     *
     * @param
     */
    void update(UpdateClassDTO updateClassDTO);
}
