package com.example.lys01.controller;

import com.example.lys01.Result.R;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.Category;
import com.example.lys01.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //分页查询

    @GetMapping("/page")
    public R<Map<String, Object>> categoryFind(int page, int pageSize) {
        return categoryService.categoryFind(page, pageSize);
    }
    //新增菜品分类
    @PostMapping
    public R<String> categorySave(@RequestBody Category category, HttpServletRequest request) {
        category.setId(SnowflakeIdGenerator.getInstance().nextId());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Long userId = (Long) request.getSession().getAttribute("employeeId");
        if (userId == null) {
            userId = 1L;
        }
        category.setCreateUser(userId);
        category.setUpdateUser(userId);
        return categoryService.categoryAdd(category);
    }
    //修改菜品分类
    @PutMapping
    public R<String> categoryUpdate(@RequestBody Category category, HttpServletRequest request) {
        category.setUpdateTime(LocalDateTime.now());
        Long userId = (Long) request.getSession().getAttribute("employeeId");
        if (userId == null) {
            userId = 1L;
        }
        category.setUpdateUser(userId);
        return categoryService.categoryUpdate(category);
    }
    //删除菜品分类
    @DeleteMapping
    public R<String> categoryDelete(Long ids) {
        return categoryService.categoryDelete(ids);
    }

    @GetMapping("/list")
    public R<List<Category>> getList(Integer type) {
        if (type == null)
            return R.success(categoryService.list());
        else
        return categoryService.getList(type);
    }
}
