package com.sky.mapper;

import com.sky.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseStudentMapper {
    /**
     * 课程添加学生
     * studentId
     */
    @Insert("insert into course_student(course_id, student_id,student_name,teacher_id)" +
            " VALUES" +
            " (#{courseId}, #{studentId},#{studentName},#{teacherId})")
    void insert(Long courseId,Long studentId,String studentName,Long teacherId);

    /**
     * 查询课程所有学生
     *courseId
     */
    @Select("Select student_id,student_name from course_student where course_id = #{courseId}")
    List<UserVO> selectAllStudents(Long courseId);

    /**
     * 查询我的所有学生
     *teacherId
     */
    @Select("SELECT DISTINCT \n" +
            "        s.student_id, \n" +
            "        s.student_name \n" +
            "    FROM course_student s\n" +
            "    WHERE s.teacher_id = #{teacherId}")
    List<UserVO> selectStudentsByTeacherId(Long teacherId);

    /**
     * 查询学生所有
     *courseId
     */
    @Select("Select course_id from course_student where student_id = #{studentId}")
    List<Long> selectMyCourseIds(Long studentId);
}
