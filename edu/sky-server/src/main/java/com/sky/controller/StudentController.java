package com.sky.controller;

import com.sky.dto.*;
import com.sky.entity.Course;
import com.sky.entity.UserHomework;
import com.sky.mapper.CourseStudentMapper;
import com.sky.result.Result;
import com.sky.service.StudentService;
import com.sky.service.UserService;
import com.sky.vo.CourseDetailVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生操作
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseStudentMapper courseStudentMapper;
    @Autowired
    private StudentService studentService;

    /**
     * 加入课程
     *
     * @param courseId
     * @return
     */
    @PostMapping("/joincourse")
    @ApiOperation("加入课程")
    public Result joinCourse(@RequestParam Long courseId,@RequestParam Long studentId) {
        studentService.addCourseStudent(studentId,courseId);
        log.info("学生"+studentId+"成功加入课程"+courseId);
        return Result.success("学生"+studentId+"成功加入课程"+courseId);
    }

    /**
     * 提交作业
     *
     * @param submitHomeworkDTO
     * @return
     */
    @PostMapping("/submithomework")
    @ApiOperation("提交作业")
    public Result getHomeworks(@RequestBody SubmitHomeworkDTO submitHomeworkDTO)
    {
        studentService.submitHomework(submitHomeworkDTO);
        log.info("学生"+submitHomeworkDTO.getStudentId()+"提交作业"+submitHomeworkDTO.getHomeworkId());
        return Result.success("学生"+submitHomeworkDTO.getStudentId()+"提交作业"+submitHomeworkDTO.getHomeworkId());
    }

    /**
     * 获取课程详细信息
     *
     *
     * @return
     */
    @GetMapping("/getdetail/{courseid}")
    @ApiOperation("获取课程详细信息")
    public Result getById(@PathVariable Long courseid)
    {
        log.info("根据课程号"+courseid+"获取课程详细信息");
        CourseDetailVO courseDetailVO=studentService.getCoursesDetailInfo(courseid);
        log.info("1"+courseDetailVO.getClasses());
        return Result.success(courseDetailVO);
    }

    /**
     * 获取单个作业完成情况
     *
     *homeworkid
     * @return
     */
    @GetMapping("/gethomeworkdetail")
    @ApiOperation("获取单个作业完成情况")
    public Result<UserHomework> getHomeworkDetail(@RequestParam Long homeworkId,@RequestParam Long studentId)
    {
        UserHomework userHomework =studentService.getAHomework(homeworkId,studentId);
        log.info("根据作业号"+homeworkId+"获取学生作业详细信息");
        return Result.success(userHomework);
    }

    /**
     * 获取学生加入的所有课程
     *
     * studentid
     * @return
     */
    @GetMapping("/getmycourses/{studentId}")
    @ApiOperation("获取学生加入的所有课程")
    public Result getMyCourses(@PathVariable Long studentId)
    {
        List<Course> courses=studentService.getMyBaseCourses(studentId);
        log.info("获取学生"+studentId+"加入的所有课程");
        return Result.success(courses);
    }
}