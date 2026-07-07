package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select * from category order by sort asc limit #{page}, #{pageSize}")
    List<Category> categoryFind(int page, int pageSize);

    @Select("select count(*) from category")
    int categoryCount();

    @Select("select * from category where name = #{name}")
    Category categoryFindByName(String name);

    @Insert("insert into category(id, type, name, sort, create_time, update_time, create_user, update_user) " +
            "values(#{id}, #{type}, #{name}, #{sort}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void categoryAdd(Category category);

    @Update("update category set name = #{name}, sort = #{sort}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    int categoryUpdate(Category category);

    @Delete("delete from category where id = #{id}")
    int categoryDelete(Long id);

    @Select("SELECT COUNT(*) FROM dish WHERE category_id = #{id}")
    int countByDish(Long id);

    @Select("SELECT COUNT(*) FROM setmeal WHERE category_id = #{id}")
    int countBySetmeal(Long id);

    @Select("SELECT * FROM category WHERE type = #{type}")
    List<Category>getList(int type);
}
