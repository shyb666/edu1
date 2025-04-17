package com.sky.service.impl;

import com.sky.dto.AddHomeworkDTO;
import com.sky.dto.ClassDTO;
import com.sky.dto.CourseDTO;
import com.sky.dto.UpdateClassDTO;
import com.sky.entity.Course;
import com.sky.entity.HomeWork;
import com.sky.entity.User;
import com.sky.entity.UserHomework;
import com.sky.mapper.*;
import com.sky.service.TeacherService;
import com.sky.vo.SubmitHomeworkVO;
import com.sky.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class TeacherSerciveIplm implements TeacherService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseStudentMapper courseStudentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private HomeworkStudentMapper homeworkStudentMapper;
    @Override
    public void createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(courseDTO, course);
        course.setCreateTime(LocalDateTime.now());
        course.setClassNum(0);
        course.setHomeworkNum(0);
        course.setStudentNum(0);
        courseMapper.insert(course);
    }

    @Override
    public List<Course> getMyCreatedBaseCourses(Long teacherId) {
            List<Course> courses=courseMapper.getByTeacherId(teacherId);
            return courses;

    }

    @Override
    public List<UserVO> getStudents(Long courseId) {
        List<UserVO> userVOS=courseStudentMapper.selectAllStudents(courseId);
        return userVOS;
    }

    @Override
    public List<UserVO> getMyStudents(Long teacherId) {
        List<UserVO> userVOS=courseStudentMapper.selectStudentsByTeacherId(teacherId);
        return userVOS;
    }

    @Override
    public void insertClass(ClassDTO classDTO) {
        com.sky.entity.Class aclass = new com.sky.entity.Class();
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(classDTO, aclass);
         classMapper.insert(aclass);
         courseMapper.addClassNum(classDTO.getCourseId());
    }

    @Override
    public void updateClass(UpdateClassDTO updateClassDTO) {
        classMapper.update(updateClassDTO);
    }

    @Override
    public void addHomework(AddHomeworkDTO addHomeworkDTO) {

        homeworkMapper.insert(addHomeworkDTO);
        courseMapper.addHomeworkNum(addHomeworkDTO.getCourseId());
    }

    @Override
    public void deleteHomework(Long homeworkId) {
        homeworkMapper.deleteHomework(homeworkId);
        homeworkStudentMapper.deleteHomeworkByHomeworkId(homeworkId);
    }

    @Override
    public SubmitHomeworkVO getSubmitHomework(Long homeworkId, Long studentId) {
        SubmitHomeworkVO submitHomeworkVO = new SubmitHomeworkVO();
        UserHomework homeWork=homeworkStudentMapper.getHomeworkById(homeworkId,studentId);
        if(homeWork!=null)
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(homeWork,submitHomeworkVO);
        else{
            submitHomeworkVO=null;
        }
        return submitHomeworkVO;
    }

    @Override
    public void setHomeworkScore(Long homeworkId, Long studentId,int score) {
        UserHomework userHomework=new UserHomework();
        userHomework.setHomeworkId(homeworkId);
        userHomework.setStudentId(studentId);
        userHomework.setHomeworkScore(score);
        userHomework.setHomeworkState(1);
        log.info("准备更新分数");
        homeworkStudentMapper.update(userHomework);
    }
}
