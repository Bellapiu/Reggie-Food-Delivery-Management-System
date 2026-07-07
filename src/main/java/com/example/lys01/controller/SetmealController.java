package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lys01.Result.R;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.*;
import com.example.lys01.service.CategoryService;
import com.example.lys01.service.DishFlavorService;
import com.example.lys01.service.SetmealDishService;
import com.example.lys01.service.SetmealService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> addSetmeal(@RequestBody Setmeal setmeal, HttpServletRequest request) {
        Employee dengluren = (Employee) request.getSession().getAttribute("employee");
        if (dengluren == null) {
            return R.error("用户未登录");
        }
        Long user = dengluren.getId();
        setmeal.setCreateUser(user);
        setmeal.setUpdateUser(user);
        setmeal.setId(SnowflakeIdGenerator.getInstance().nextId());
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setStatus(0);

        SetmealDao setmealDao = new SetmealDao();
        setmealDao.setId(setmeal.getId());
        setmealDao.setCategoryId(setmeal.getCategoryId());
        setmealDao.setName(setmeal.getName());
        setmealDao.setPrice(setmeal.getPrice());
        setmealDao.setStatus(setmeal.getStatus());
        setmealDao.setCode(setmeal.getCode());
        setmealDao.setDescription(setmeal.getDescription());
        setmealDao.setImage(setmeal.getImage());
        setmealDao.setCreateTime(setmeal.getCreateTime());
        setmealDao.setUpdateTime(setmeal.getUpdateTime());
        setmealDao.setCreateUser(setmeal.getCreateUser());
        setmealDao.setUpdateUser(setmeal.getUpdateUser());

        return setmealService.save(setmealDao) ? R.success("套餐添加成功") : R.error("套餐添加失败");
    }

    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDao setmealDao, HttpServletRequest request) {
        Employee dengluren = (Employee) request.getSession().getAttribute("employee");
        if (dengluren == null) {
            return R.error("用户未登录");
        }
        
        Long userId = dengluren.getId();
        setmealDao.setUpdateUser(userId);
        setmealDao.setUpdateTime(LocalDateTime.now());
        
        boolean result = setmealService.updateSetmeal(setmealDao);
        return result ? R.success("套餐修改成功") : R.error("套餐修改失败");
    }

    @GetMapping("/{id}")
    public R<SetmealDao> getSetmealById(@PathVariable Long id) {
        SetmealDao setmealDao = setmealService.getSetmealWithDishes(id);
        if (setmealDao != null) {
            return R.success(setmealDao);
        } else {
            return R.error("套餐不存在");
        }
    }

    @GetMapping("/page")
    public R<Map<String, Object>> page(Integer page, Integer pageSize,String name) {
        List<SetmealDao> list=setmealService.list();
        for(SetmealDao st:list){
            st.setCategoryName(categoryService.getById(st.getCategoryId()).getName());
        }

        Map<String, Object>map=new HashMap<>();
        map.put("total",list.size());
        map.put("records",list);
        return R.success(map);
    }
    @GetMapping("/dish/{id}")
    public R<List<DishFlavor>> getDish(@PathVariable Long id) {
        QueryWrapper<DishFlavor> queryWrapper = new QueryWrapper();
        queryWrapper.eq("dish_id", id);
        List<DishFlavor> setmealDishes =  dishFlavorService.list(queryWrapper);
         return R.success(setmealDishes);
    }
//    http://localhost:8080/setmeal/list?categoryId=1413386191767674881&status=1
    @GetMapping("/list")
    public R<List<SetmealDao>> list(Long categoryId, Integer status) {
        QueryWrapper<SetmealDao> q = new QueryWrapper<>();
       q.eq("category_id", categoryId);
        q.eq("status", status);
        return R.success(setmealService.list(q));
    }
}
