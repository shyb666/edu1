package com.sky.controller;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.HomeWork;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.StudentService;
import com.sky.service.UserService;
import com.sky.utils.AliOssUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户通用操作
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AliOssUtil aliOssUtil;
    //springmvc 框架中使用MultipartFile接受文件,传到阿里云服务器
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上次：{}",file);
        try {
            //原始文件名
            String originalFilename=file.getOriginalFilename();
            //取后缀扩展名
            String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名
            String objectName= UUID.randomUUID().toString()+extension;
            //文件的请求路径
            String filePath =aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        }
        catch(IOException e){
            e.printStackTrace();
            log.error("上传失败");
        }
        return Result.error("文件上传失败");
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getPhone())
                .name(user.getName())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     *  注册
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result getHomework(@RequestBody UserDTO  userDTO) {
        userService.save(userDTO);
        log.info("注册用户"+userDTO.getName());
        return Result.success("注册用户"+userDTO.getName());
    }


    /**
     * 根据用户id获取数据
     *
     * userId
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据用户id获取数据")
    public Result<User> login(@PathVariable Long id) {
        log.info("根据用户id获取数据"+id);
       User user= userService.getById(id);
       return Result.success(user);

    }

    /**
     *  获取布置的作业
     *
     * @param courseId
     * @return
     */
    @GetMapping("/gethomework/{courseId}")
    @ApiOperation("获取布置的作业")
    public Result getHomework(@PathVariable Long courseId) {
        log.info("获取课程"+courseId+"的作业");
        List<HomeWork> homeWorks= userService.getHomeworkList(courseId);
        log.info("获取课程"+courseId+"的作业");
        return Result.success(homeWorks);
    }

    /**
     * 获取课程粗略信息（包括课表）
     *
     *
     * @return
     */
    @GetMapping("/courseinfo/{courseId}")
    @ApiOperation("获取课程信息")
    public Result<CourseVO> getCoursesInfo(@PathVariable Long courseId)
    {
        CourseVO courseVO=userService.getCoursesInfo(courseId);
        log.info("获取课程信息"+ courseVO);
        return Result.success(courseVO);
    }

    /**
     * 获取全部课程基本信息
     *
     *
     * @return
     */

    @GetMapping("/courselist")
    @ApiOperation("获取全部课程基本信息")
    public Result getAllCourses()
    {
        List<BaseCourseVO> baseCourseVOS=userService.getAllBaseCourses();
        log.info("获取全部课程基本信息");
        return Result.success(baseCourseVOS);
    }

    /**
     * 根据名称获取全部课程基本信息
     *
     *@PathVariable String courseName
     * @return
     */

    @GetMapping("/courselist/{courseName}")
    @ApiOperation("根据名称获取全部课程基本信息")
    public Result getCoursesByName(@PathVariable String courseName)
    {
        List<BaseCourseVO> baseCourseVOS=userService.getAllBaseCoursesByName(courseName);
        log.info("根据名称获取全部课程基本信息:"+courseName);
        return Result.success(baseCourseVOS);
    }

    /**
     * 根据类型获取全部课程基本信息
     *
     *@PathVariable String type
     * @return
     */

    @GetMapping("/courselistbytype/{type}")
    @ApiOperation("根据类型获取全部课程基本信息")
    public Result getCoursesByType(@PathVariable String type)
    {
        log.info("根据类型获取全部课程基本信息"+type);
        List<BaseCourseVO> baseCourseVOS=userService.getAllBaseCoursesByType(type);
        log.info("获取全部课程基本信息");
        return Result.success(baseCourseVOS);
    }

    /**
     *  获取该课程作业学生的完成情况
     *
     * homeworkId, score, studentId
     * @return
     */
    @GetMapping("/gethomeworkscore")
    @ApiOperation("获取该课程作业学生的完成情况")
    public Result getSubmitHomework(@RequestParam Long courseId, @RequestParam Long studentId) {
        List<UserHomeworkVO> userHomeworkVOS= studentService.getHomework(courseId,studentId);
        log.info("获取该课程作业学生的完成情况");
        return Result.success(userHomeworkVOS);
    }
}
