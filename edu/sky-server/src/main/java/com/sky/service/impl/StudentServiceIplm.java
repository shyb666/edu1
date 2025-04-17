package com.sky.service.impl;

import com.sky.dto.SubmitHomeworkDTO;
import com.sky.entity.Course;
import com.sky.entity.HomeWork;
import com.sky.entity.UserHomework;
import com.sky.mapper.*;
import com.sky.service.StudentService;
import com.sky.vo.BaseCourseVO;
import com.sky.vo.CourseDetailVO;
import com.sky.vo.UserHomeworkVO;
import com.sky.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 肖某
 */
@Service
public class StudentServiceIplm  implements StudentService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CourseStudentMapper courseStudentMapper;
    @Autowired
    private HomeworkStudentMapper homeworkStudentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public CourseDetailVO getCoursesDetailInfo(Long courseId) {
        CourseDetailVO courseDetailVO = new CourseDetailVO();
        Course course = courseMapper.getById(courseId);
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(course, courseDetailVO);
        List<UserVO> users = courseStudentMapper.selectAllStudents(courseId);
        List<HomeWork> homeWorks = homeworkMapper.getAllHomework(courseId);
        List<com.sky.entity.Class> classes = classMapper.selectAllClasses(courseId);
        courseDetailVO.setStudents(users);
        courseDetailVO.setHomeworks(homeWorks);
        courseDetailVO.setClasses(classes);
        return courseDetailVO;
    }

    @Override
    public List<BaseCourseVO> getCoursesByType(String type) {
          List<BaseCourseVO> baseCourseVOS = new ArrayList<>();
          List<Course> courses=courseMapper.getByType(type);
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(courses, baseCourseVOS);
        return baseCourseVOS;
    }

    @Override
    public List<BaseCourseVO> getCoursesByName(String courseName) {
        List<BaseCourseVO> baseCourseVOS = new ArrayList<>();
        List<Course> courses=courseMapper.getByName(courseName);
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(courses, baseCourseVOS);
        return baseCourseVOS;
    }

    @Override
    public List<BaseCourseVO> getAllBaseCourses() {
        List<BaseCourseVO> baseCourseVOS = new ArrayList<>();
        List<Course> courses=courseMapper.getAllCourse();
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(courses, baseCourseVOS);
        return baseCourseVOS;
    }

    @Override
    public List<Course> getMyBaseCourses(Long studentId) {
        List<Long> courseIds=courseStudentMapper.selectMyCourseIds(studentId);
        List<Course> courses= new ArrayList<>();
        for(Long courseId : courseIds){
           Course course= courseMapper.getById(courseId);
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void addCourseStudent(Long id, Long courseId) {
        String studentName=userMapper.getById(id).getName();
        Long teacherId=courseMapper.getById(courseId).getTeacherId();
        courseStudentMapper.insert(courseId,id,studentName,teacherId);
        courseMapper.addStudentNum(courseId);
    }

    @Override
    public List<UserHomeworkVO> getHomework(Long courseId,Long studentId) {
        List<HomeWork> homeWorks = homeworkMapper.getAllHomework(courseId);
        List<UserHomeworkVO> userHomeworkVOS = new ArrayList<>();
        for(HomeWork homeWork : homeWorks){
            UserHomeworkVO vo = new UserHomeworkVO();
            // 拷贝布置的作业属性
            vo.setHomeworkName(homeWork.getHomeworkName());
            vo.setHomeworkContent(homeWork.getHomeworkContent());
            vo.setHomeworkUrl(homeWork.getHomeworkUrl());
            vo.setState(homeWork.getState());
            //拷贝学生完成情况
            UserHomework userHomework=homeworkStudentMapper.getHomeworkById(homeWork.getHomeworkId(),studentId);
            vo.setHsId(userHomework.getHsId().intValue());
            vo.setId(userHomework.getHomeworkId());
            vo.setName(userMapper.getById(studentId).getName());
            vo.setScore(userHomework.getHomeworkScore());
            vo.setSubmitContent(userHomework.getHomeworkContent());
            vo.setSubmitUrl(userHomework.getHomeworkUrl());
            //添加到list
            userHomeworkVOS.add(vo);
        }

        return userHomeworkVOS;
    }

    @Override
    public UserHomework getAHomework(Long homeworkId, Long studentId) {
        UserHomework userHomework= homeworkStudentMapper.getHomeworkById(homeworkId,studentId);
        return userHomework;
    }

    @Override
    public void deleteCourseStudent(Long id, Long courseId) {

    }

    @Override
    public void submitHomework(SubmitHomeworkDTO submitHomeworkDTO) {
        UserHomework userHomework = new UserHomework();
        //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(submitHomeworkDTO, userHomework);
       userHomework.setHomeworkScore(0);
       userHomework.setHomeworkState(1);
          homeworkStudentMapper.insert(userHomework);
    }
}
