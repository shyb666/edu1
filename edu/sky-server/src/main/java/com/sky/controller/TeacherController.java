package com.sky.controller;
import com.sky.dto.AddHomeworkDTO;
import com.sky.dto.ClassDTO;
import com.sky.dto.CourseDTO;
import com.sky.dto.UpdateClassDTO;
import com.sky.entity.Course;
import com.sky.result.Result;
import com.sky.service.TeacherService;
import com.sky.vo.SubmitHomeworkVO;
import com.sky.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 教师操作
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;


    /**
     * 上传课程
     *
     * courseDTO
     * @return
     */
    @PostMapping("/postcourse")
    @ApiOperation("上传课程")
    public Result postCourse(@RequestBody CourseDTO courseDTO) {
        log.info("***"+String.valueOf(courseDTO));
        teacherService.createCourse(courseDTO);
        log.info("成功创建课程");
        return Result.success("成功创建课程");
    }

    /**
     *  添加课表（每次添加单节课）
     *
     * @param classDTO
     * @return
     */
    @PostMapping("/postclass")
    @ApiOperation("添加课表（每次添加单节课）")
    public Result postClass(@RequestBody ClassDTO classDTO) {
        teacherService.insertClass(classDTO);
        log.info("成功为课表添加了课程"+classDTO.getClassName());
        return Result.success("成功为课表添加了课程"+classDTO.getClassName());
    }

    /**
     *  修改课表
     *
     * @param updateClassDTO
     * @return
     */
    @PutMapping("/updateclass")
    @ApiOperation("修改课表")
    public Result postClass(@RequestBody UpdateClassDTO updateClassDTO) {
        teacherService.updateClass(updateClassDTO);
        log.info("成功修改课表"+updateClassDTO);
        return Result.success("成功修改课表"+updateClassDTO);
    }

    /**
     *  布置作业
     *
     * @param addHomeworkDTO
     * @return
     */
    @PostMapping("/posthomework")
    @ApiOperation("布置作业")
    public Result postHomework(@RequestBody AddHomeworkDTO addHomeworkDTO) {
        teacherService.addHomework(addHomeworkDTO);
        log.info("成功为课程"+addHomeworkDTO.getCourseId()+"布置了作业"+addHomeworkDTO.getHomeworkName());
        return Result.success("成功为课程"+addHomeworkDTO.getCourseId()+"布置了作业"+addHomeworkDTO.getHomeworkName());
    }

    /**
     *  获取学生提交的作业
     *
     * homeworkId, studentId
     * @return
     */
    @GetMapping("/getsubmithomework")
    @ApiOperation("获取学生提交的作业")
    public Result<SubmitHomeworkVO> getSubmitHomework(@RequestParam Long homeworkId, @RequestParam Long studentId) {
        SubmitHomeworkVO submitHomeworkVO=teacherService.getSubmitHomework(homeworkId,studentId);
        log.info("成功获取学生"+studentId +"的作业");
        return Result.success(submitHomeworkVO);
    }

    /**
     *  作业评分
     *
     * homeworkId, score, studentId
     * @return
     */
    @PutMapping("/setscore")
    @ApiOperation("作业评分")
    public Result setHomeworkScore(@RequestParam Long homeworkId, @RequestParam int score, @RequestParam Long studentId) {
        teacherService.setHomeworkScore(homeworkId,studentId,score);
        log.info("成功为作业"+homeworkId+"的学生"+studentId +"评分"+score);
        return Result.success("成功为作业"+homeworkId+"的学生"+studentId +"评分"+score);
    }

    /**
     *  获取该课程学生列表
     *
     * homeworkId, score, studentId
     * @return
     */
    @GetMapping("/getstudents/{courseId}")
    @ApiOperation("学生列表")
    public Result getStudents(@PathVariable Long courseId) {
        log.info("获取课程"+courseId+"的学生");
        List<UserVO> users=teacherService.getStudents(courseId);
        log.info("获取课程"+courseId+"的学生"+users);
        return Result.success(users);
    }

    @GetMapping("/getmystudents/{teacherId}")
    @ApiOperation("学生列表")
    public Result getMyStudents(@PathVariable Long teacherId) {
        log.info("获取教师"+teacherId+"的所有学生");
        List<UserVO> users=teacherService.getMyStudents(teacherId);
        log.info("获取教师"+teacherId+"的所有学生"+users);
        return Result.success(users);
    }


    /**
     *  获取我创建的课程
     *
     * homeworkId, score, studentId
     * @return
     */
    @GetMapping("/getmycourses")
    @ApiOperation("获取我创建的课程")
    public Result getMycourses(@RequestParam Long teacherId) {
        List<Course> courses=teacherService.getMyCreatedBaseCourses(teacherId);
        log.info("获取教师"+teacherId+"的课程"+courses);
        return Result.success(courses);
    }

}
