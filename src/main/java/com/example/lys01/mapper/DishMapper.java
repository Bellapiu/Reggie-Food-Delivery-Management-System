package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.Dish;
import com.example.lys01.entry.DishDD;
import com.example.lys01.entry.DishFlavor;
import com.example.lys01.entry.DishFlavorDD;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<DishFlavorDD>{
    @Select("SELECT dish.*, category.name AS categoryName FROM dish LEFT JOIN category ON dish.category_id = category.id ORDER BY dish.sort ASC LIMIT #{page}, #{pageSize}")
    List<Dish> dishFind(int page, int pageSize);

    @Select("select count(*) from dish")
    int dishCount();

    @Select("select * from dish where name = #{name}")
    Dish dishFindByName(String name);

    @Insert("insert into dish(id, name, category_id, price, code, image, description, status, sort, create_time, update_time, create_user, update_user) " +
            "values(#{id}, #{name}, #{categoryId}, #{price}, #{code}, #{image}, #{description}, #{status}, #{sort}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void dishAdd(Dish dish);

    @Update("update dish set name = #{name}, category_id = #{categoryId}, price = #{price}, code = #{code}, image = #{image}, " +
            "description = #{description}, status = #{status}, sort = #{sort}, update_time = #{updateTime}, update_user = #{updateUser} " +
            "where id = #{id}")
    int dishUpdate(Dish dish);

    @Delete("delete from dish where id = #{id}")
    int dishDelete(Long id);

    @Select("SELECT dish.*, category.name AS categoryName FROM dish LEFT JOIN category ON dish.category_id = category.id WHERE dish.name LIKE CONCAT('%', #{name}, '%') ORDER BY dish.sort ASC LIMIT #{offset}, #{pageSize}")
    List<Dish> dishFindByNamePage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("name") String name);

    @Select("SELECT COUNT(*) FROM dish WHERE name LIKE CONCAT('%', #{name}, '%')")
    int dishCountByName(@Param("name") String name);
    
    @Insert("insert into dish(id,name,category_id,price,code,image,description,status,sort,create_time,update_time,create_user,update_user) " +
            "values(#{id},#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    int addDish(DishFlavorDD dishFlavorDD);

    @Select("select * from dish where name=#{name}")
    List<Dish> getByName(String name);
    
    @Delete("<script>" +
            "delete from dish where id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int dishDeleteBatch(@Param("ids") List<Long> ids);

    @Update("<script>" +
            "update dish set status = #{status}, update_time = now() where id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int dishUpdateStatusBatch(@Param("ids") List<Long> ids, @Param("status") Integer status);

    @Select("SELECT dish.*, category.name AS categoryName FROM dish LEFT JOIN category ON dish.category_id = category.id WHERE dish.id = #{id}")
    DishDD getDishById(Long id);

    @Select("<script>" +
            "SELECT dish.*, category.name AS categoryName FROM dish LEFT JOIN category ON dish.category_id = category.id" +
            "<where>" +
            "<if test='name != null and name != \"\"'> AND dish.name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='categoryId != null'> AND dish.category_id = #{categoryId}</if>" +
            "<if test='status != null'> AND dish.status = #{status}</if>" +
            "</where>" +
            " ORDER BY dish.sort ASC" +
            "</script>")
    List<Dish> dishList(Dish dish);
    
    @Select("SELECT * FROM dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> getFlavorsByDishId(Long dishId);
}
