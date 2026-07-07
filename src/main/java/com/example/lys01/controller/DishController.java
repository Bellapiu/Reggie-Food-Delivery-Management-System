package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lys01.Result.R;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.Dish;
import com.example.lys01.entry.DishFlavor;
import com.example.lys01.entry.DishFlavorDD;
import com.example.lys01.entry.Employee;
import com.example.lys01.service.DishService;
import com.example.lys01.service.DishFlavorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    
    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/page")
    public R<Map<String, Object>> dishFind(int page, int pageSize, String name) {
        if (name != null && !name.trim().isEmpty()) {
            return dishService.dishFindByName(page, pageSize, name);
        } else {
            return dishService.dishFind(page, pageSize);
        }
    }

    @PostMapping
    public R<String> addDish(@RequestBody DishFlavorDD dishFlavorDD, HttpServletRequest request) {
        Employee dengluren = (Employee) request.getSession().getAttribute("employee");
        Long user = dengluren.getId();
        dishFlavorDD.setCreateUser(user);
        dishFlavorDD.setUpdateUser(user);
        dishFlavorDD.setId(SnowflakeIdGenerator.getInstance().nextId());
        dishFlavorDD.setCreateTime(LocalDateTime.now());
        dishFlavorDD.setUpdateTime(LocalDateTime.now());
        dishFlavorDD.setSort(0);
        dishFlavorDD.setIsDeleted(0);

        return dishService.dishAdd(dishFlavorDD);
    }

    @PutMapping
    public R<String> dishUpdate(@RequestBody Dish dish, HttpServletRequest request) {
        dish.setUpdateTime(LocalDateTime.now());
        Long userId = (Long) request.getSession().getAttribute("employeeId");
        if (userId == null) {
            userId = 1L;
        }
        dish.setUpdateUser(userId);
        return dishService.dishUpdate(dish);
    }

    @DeleteMapping
    public R<String> dishDelete(@RequestBody Object ids) {
        List<Long> idList;
        
        if (ids instanceof String) {
            String idsStr = (String) ids;
            if (idsStr.contains(",")) {
                idList = Arrays.stream(idsStr.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            } else {
                idList = Arrays.asList(Long.parseLong(idsStr.trim()));
            }
        } else if (ids instanceof List) {
            idList = ((List<?>) ids).stream()
                    .map(obj -> {
                        if (obj instanceof Number) {
                            return ((Number) obj).longValue();
                        } else if (obj instanceof String) {
                            return Long.parseLong((String) obj);
                        } else {
                            return Long.parseLong(obj.toString());
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return R.error("参数格式错误");
        }
        
        if (idList == null || idList.isEmpty()) {
            return R.error("请选择要删除的菜品");
        }
        
        return dishService.dishDeleteBatch(idList);
    }

    @GetMapping("/{id}")
    public R<DishFlavorDD> dishGetById(@PathVariable Long id) {
        return dishService.dishGetById(id);
    }



    @PostMapping("/status/{status}")
    public R<String> dishUpdateStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return R.error("请选择要操作的菜品");
        }
        
        if (status != 0 && status != 1) {
            return R.error("状态参数错误");
        }
        
        return dishService.dishUpdateStatusBatch(ids, status);
    }

    @GetMapping("/list")
    public R<List<DishFlavorDD>> list(Long categoryId) {
        QueryWrapper<DishFlavorDD> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "category_id", categoryId);
        List<DishFlavorDD> listss= dishService.list(queryWrapper);

        for (DishFlavorDD d : listss){
            QueryWrapper<DishFlavor> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq( "dish_id", d.getId());
            d.setFlavors(dishFlavorService.list(queryWrapper2));
        }
        return R.success(listss);
    }
}
