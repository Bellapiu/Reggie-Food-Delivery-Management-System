package com.example.lys01.mapper;

import com.example.lys01.entry.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where name = #{name}")
    Employee getByname(String name);

    //根据名字获取分页
    @Select("select * from employee where name like concat('%',#{name},'%') limit #{page},#{pageSize}")
    List<Employee> getByName(String name, int page, int pageSize);

    //获取所有员工 分页
    @Select("select * from employee limit #{page},#{pageSize}")
    List<Employee> getAll(int page, int pageSize);

    //根据username获取
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    //添加员工
    @Insert("insert into employee(id, username, name, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values(#{id}, #{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    int addEmployee(Employee employee);
    //更新状态，修改时间，修改人
    @Update("update employee set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    int updateStatus(Employee employee);

    @Update("<script>" +
            "update employee set " +
            "<if test='username != null'>username = #{username},</if>" +
            "<if test='name != null'>name = #{name},</if>" +
            "<if test='phone != null'>phone = #{phone},</if>" +
            "<if test='sex != null'>sex = #{sex},</if>" +
            "<if test='idNumber != null'>id_number = #{idNumber},</if>" +
            "update_time = #{updateTime}, " +
            "update_user = #{updateUser} " +
            "where id = #{id}" +
            "</script>")
    int update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);


    

}
