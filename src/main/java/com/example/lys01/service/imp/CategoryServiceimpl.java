package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.Result.R;
import com.example.lys01.entry.Category;
import com.example.lys01.mapper.CategoryMapper;
import com.example.lys01.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R<Map<String, Object>> categoryFind(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Category> categories = categoryMapper.categoryFind((page - 1) * pageSize, pageSize);
        int total = categoryMapper.categoryCount();
        map.put("records", categories);
        map.put("total", total);
        return R.success(map);
    }

    //新增菜品分类
    @Override
    public R<String> categoryAdd(Category category) {
        Category existingCategory = categoryMapper.categoryFindByName(category.getName());
        if (existingCategory != null) {
            return R.error("分类名称已存在");
        }
        categoryMapper.categoryAdd(category);
        return R.success("添加分类成功");
    }

    @Override
    public R<String> categoryUpdate(Category category) {
        System.out.println("修改分类 - ID: " + category.getId() + ", Name: " + category.getName() + ", Sort: " + category.getSort());
        Category existingCategory = categoryMapper.categoryFindByName(category.getName());
        if (existingCategory != null && !existingCategory.getId().equals(category.getId())) {
            return R.error("分类名称已存在");
        }
        int rows = categoryMapper.categoryUpdate(category);
        System.out.println("修改影响行数: " + rows);
        if (rows > 0) {
            return R.success("修改分类成功");
        } else {
            return R.error("修改分类失败，未找到对应记录");
        }
    }

    @Override
    public R<String> categoryDelete(Long id) {
        System.out.println("删除分类 - ID: " + id);
        
        int dishCount = categoryMapper.countByDish(id);
        if (dishCount > 0) {
            return R.error("该分类下还有菜品，不能删除");
        }
        
        int setmealCount = categoryMapper.countBySetmeal(id);
        if (setmealCount > 0) {
            return R.error("该分类下还有套餐，不能删除");
        }
        
        int rows = categoryMapper.categoryDelete(id);
        System.out.println("删除影响行数: " + rows);
        if (rows > 0) {
            return R.success("删除分类成功");
        } else {
            return R.error("删除分类失败，未找到对应记录");
        }
    }
    @Override
    public R<List<Category>> getList(int type){
        return R.success(categoryMapper.getList(type));
    }
}
