package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    @Insert("insert into dish_flavor(id,dish_id,name,value,create_time,update_time,create_user,update_user,is_deleted) " +
            "values(#{id},#{dishId},#{name},#{value},now(),now(),#{createUser},#{updateUser},0)")
    int addDishFlavor(DishFlavor flavor);

    @Delete("<script>" +
            "delete from dish_flavor where dish_id in " +
            "<foreach collection='list' item='dishId' open='(' separator=',' close=')'>" +
            "#{dishId}" +
            "</foreach>" +
            "</script>")
    int deleteFlavorByDishIds(List<Long> ids);

    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    int deleteFlavorByDishId(@Param("dishId") Long dishId);
}
