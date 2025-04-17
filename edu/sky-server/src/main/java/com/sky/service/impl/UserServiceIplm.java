package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.dto.*;
import com.sky.entity.Course;
import com.sky.entity.HomeWork;
import com.sky.entity.User;
import com.sky.entity.UserHomework;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.ClassMapper;
import com.sky.mapper.CourseMapper;
import com.sky.mapper.HomeworkMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.UserService;
import com.sky.vo.BaseCourseVO;
import com.sky.vo.CourseVO;
import com.sky.vo.UserHomeworkVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceIplm implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        String password = userLoginDTO.getPassword();
        //1、根据账号查询数据库中的数据
        User user = userMapper.getByPhone(phone);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对（已完成）
        password = DigestUtils.md5DigestAsHex(password.getBytes());  //使用MD5工具类对密码进行加密（不可反向编译）
        System.out.println(password);
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //3、返回实体对象
        return user;
    }


    /**
     * 新增用户
     *
     * @param userDTO
     */
    @Override
    public void save(UserDTO userDTO) {
        System.out.print(userDTO.getPhone()+userDTO.getEmail());
        //把dto转化为entity（有些属性是空的）
        User user = new User();
        System.out.print(user.getPhone()+user.getEmail());
       //对象属性拷贝，能把两个类共有的属性进行复制,把前者拷贝到后者
        BeanUtils.copyProperties(userDTO, user);
       //密码加密
        String pwd=user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        user.setCourseNum(0);
        //使用userMapper插入该数据
        userMapper.insert(user);
    }

    @Override
    public List<HomeWork> getHomeworkList(Long courseId) {
        List<HomeWork> homeWorks=homeworkMapper.getAllHomework(courseId);
        return homeWorks;
    }


    /**
     * 根据id查找员工

     * @param id
     */
    @Override
    public User getById( Long id){
        User user=userMapper.getById(id);
        //隐藏密码
        user.setPassword("****");
        return user;
    }




    /**
     * 获取课程部分信息
     */
    @Override
    public CourseVO getCoursesInfo(Long courseId) {
        Course course=courseMapper.getById(courseId);
        CourseVO courseVO=new CourseVO();
        BeanUtils.copyProperties(course,courseVO);
        List<com.sky.entity.Class> classes=classMapper.selectAllClasses(courseId);
        courseVO.setClasses(classes);
        return courseVO;
    }

    @Override
    public List<BaseCourseVO> getAllBaseCourses() {
        List<Course> courses=courseMapper.getAllCourse();
        List<BaseCourseVO> baseCourseVOS=new ArrayList<>();
        for(Course course : courses){
            BaseCourseVO vo = new BaseCourseVO();
            // 拷贝布置的作业属性
            vo.setCourseId(course.getCourseId());
            vo.setCourseName(course.getCourseName());
            vo.setType(course.getType());
            vo.setCoverUrl(course.getCoverUrl());
            vo.setCreateTime(course.getCreateTime());
            vo.setTeacherId(course.getTeacherId());
            //添加到list
            baseCourseVOS.add(vo);
        }
        return baseCourseVOS;
    }

    @Override
    public List<BaseCourseVO> getAllBaseCoursesByName(String courseName) {
        List<Course> courses=courseMapper.getByName(courseName);
        List<BaseCourseVO> baseCourseVOS=new ArrayList<>();
        for(Course course : courses){
            BaseCourseVO vo = new BaseCourseVO();
            // 拷贝布置的作业属性
            vo.setCourseId(course.getCourseId());
            vo.setCourseName(course.getCourseName());
            vo.setType(course.getType());
            vo.setCoverUrl(course.getCoverUrl());
            vo.setCreateTime(course.getCreateTime());
            vo.setTeacherId(course.getTeacherId());
            //添加到list
            baseCourseVOS.add(vo);
        }
        return baseCourseVOS;
    }

    @Override
    public List<BaseCourseVO> getAllBaseCoursesByType(String type) {
        List<Course> courses=courseMapper.getByType(type);
        List<BaseCourseVO> baseCourseVOS=new ArrayList<>();
        for(Course course : courses){
            BaseCourseVO vo = new BaseCourseVO();
            // 拷贝布置的作业属性
            vo.setCourseId(course.getCourseId());
            vo.setCourseName(course.getCourseName());
            vo.setType(course.getType());
            vo.setCoverUrl(course.getCoverUrl());
            vo.setCreateTime(course.getCreateTime());
            vo.setTeacherId(course.getTeacherId());
            //添加到list
            baseCourseVOS.add(vo);
        }
        return baseCourseVOS;
    }


}
