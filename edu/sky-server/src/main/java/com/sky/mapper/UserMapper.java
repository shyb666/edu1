package com.sky.mapper;


import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据id查询用户
     * id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    /**
     * 根据手机号（账号）查询用户
     * id
     * @return
     */
    @Select("select * from user where phone = #{phone}")
    User getByPhone(String phone);

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Select("insert into user (name,phone,email,password,course_num,identity)" +
            "values"+
            "(#{name},#{phone},#{email},#{password},#{courseNum},#{identity})")
    void insert(User user);

    /**
     * 属于动态sql，在xml文件中配置
     * @param user
     */
    void update(User user);

}
