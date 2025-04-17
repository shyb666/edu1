package com.sky.mapper;
import com.sky.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    /**
     * 创建课程
     * @param course
     */
    @Insert("insert into course(course_name, teacher_id, student_num,homework_num,create_time,type,class_num,cover_url)" +
            " VALUES" +
            " (#{courseName}, #{teacherId}, #{studentNum}, #{homeworkNum}, #{createTime}, #{type}, #{classNum}, #{coverUrl})")
    void insert(Course course);

    /**
     *获取老师创建的全部课程
     * @param teacherId
     */
    @Select("select * from course where teacher_id = #{teacherId}")
    List<Course> getByTeacherId(Long teacherId);

    /**
     * 根据课程Id获取课程信息
     * @param courseId
     * @return
     */
    @Select("select * from course where course_id = #{courseId}")
    Course getById(Long courseId);



    /**
     * 根据课程名字模糊查询
     * @param name
     * @return
     */
    @Select("select * from course where course_name like concat('%', #{name}, '%')")
    List<Course> getByName(String name);

    /**
     * 根据课程类型获取课程
     * @param type
     * @return
     */
    @Select("select * from course where type = #{type}")
    List<Course> getByType(String type);

    /**
     * 根据课程id删除课程
     * @param courseId
     */
    @Delete("Delete from course where course_id = #{courseId}")
    void deleteByCourseId(Long courseId);

    /**
     * 获取全部课程的信息
     * @return
     */
    @Select("select * from course")
    List<Course> getAllCourse();

/**
 * 学生数加1
 * @return
 */
    @Update("update course set student_num=student_num+1 where course_id = #{courseId}")
    void addStudentNum(Long courseId);

    /**
     * 作业数加1
     * @return
     */
    @Update("update course set homework_num=homework_num+1 where course_id = #{courseId}")
    void addHomeworkNum(Long courseId);

    /**
     * 课程数加1
     * @return
     */
    @Update("update course set class_num=class_num+1 where course_id = #{courseId}")
    void addClassNum(Long courseId);
}
