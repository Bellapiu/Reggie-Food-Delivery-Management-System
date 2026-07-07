package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.Result.R;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.Dish;
import com.example.lys01.entry.DishDD;
import com.example.lys01.entry.DishFlavor;
import com.example.lys01.entry.DishFlavorDD;
import com.example.lys01.mapper.DishMapper;
import com.example.lys01.mapper.DishFlavorMapper;
import com.example.lys01.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DishServiceimpl extends ServiceImpl<DishMapper,DishFlavorDD> implements DishService {
    @Autowired
    private DishMapper dishMapper;
    
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public R<Map<String, Object>> dishFind(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Dish> dishes = dishMapper.dishFind((page - 1) * pageSize, pageSize);
        int total = dishMapper.dishCount();
        map.put("records", dishes);
        map.put("total", total);
        return R.success(map);
    }

    @Override
    public R<Map<String, Object>> dishFindByName(int page, int pageSize, String name) {
        Map<String, Object> map = new HashMap<>();
        int offset = (page - 1) * pageSize;
        List<Dish> dishes = dishMapper.dishFindByNamePage(offset, pageSize, name);
        int total = dishMapper.dishCountByName(name);
        map.put("records", dishes);
        map.put("total", total);
        return R.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> dishAdd(DishFlavorDD dishFlavorDD) {
        if(dishMapper.getByName(dishFlavorDD.getName()).size()==0) {
            dishMapper.addDish(dishFlavorDD);
            
            List<DishFlavor> flavors = dishFlavorDD.getFlavors();
            for (DishFlavor flavor : flavors) {
                flavor.setId(SnowflakeIdGenerator.getInstance().nextId());
                flavor.setCreateUser(dishFlavorDD.getCreateUser());
                flavor.setUpdateUser(dishFlavorDD.getUpdateUser());
                flavor.setDishId(dishFlavorDD.getId());
                dishFlavorMapper.addDishFlavor(flavor);
            }
            return R.success("添加成功！！");
        }
        else {
            return R.error("菜品存在，不能添加");
        }
    }

    @Override
    public R<String> dishUpdate(Dish dish) {
        System.out.println("修改菜品 - ID: " + dish.getId() + ", Name: " + dish.getName());
        Dish existingDish = dishMapper.dishFindByName(dish.getName());
        if (existingDish != null && !existingDish.getId().equals(dish.getId())) {
            return R.error("菜品名称已存在");
        }
        int rows = dishMapper.dishUpdate(dish);
        System.out.println("修改影响行数: " + rows);
        if (rows > 0) {
            return R.success("修改菜品成功");
        } else {
            return R.error("修改菜品失败，未找到对应记录");
        }
    }

    @Override
    public R<String> dishDelete(Long id) {
        System.out.println("删除菜品 - ID: " + id);
        int rows = dishMapper.dishDelete(id);
        System.out.println("删除影响行数: " + rows);
        if (rows > 0) {
            return R.success("删除菜品成功");
        } else {
            return R.error("删除菜品失败，未找到对应记录");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> dishDeleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return R.error("请选择要删除的菜品");
        }
        System.out.println("批量删除菜品 - IDs: " + ids);
        
        for (Long id : ids) {
            dishMapper.dishDelete(id);
            dishFlavorMapper.deleteFlavorByDishId(id);
        }
        
        System.out.println("批量删除完成，共删除: " + ids.size() + "个菜品");
        return R.success("成功删除" + ids.size() + "个菜品");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> dishUpdateStatusBatch(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty()) {
            return R.error("请选择要操作的菜品");
        }
        System.out.println("批量修改菜品状态 - IDs: " + ids + ", Status: " + status);
        
        int rows = dishMapper.dishUpdateStatusBatch(ids, status);
        System.out.println("批量修改状态影响行数: " + rows);
        
        if (rows > 0) {
            String statusText = status == 1 ? "起售" : "停售";
            return R.success("成功" + statusText + rows + "个菜品");
        } else {
            return R.error("批量修改状态失败");
        }
    }

    @Override
    public R<DishFlavorDD> dishGetById(Long id) {
        System.out.println("查询菜品详情 - ID: " + id);
        
        DishDD dish = dishMapper.getDishById(id);
        if (dish == null) {
            return R.error("未找到对应菜品");
        }
        
        DishFlavorDD dishFlavorDD = new DishFlavorDD();
        dishFlavorDD.setId(dish.getId());
        dishFlavorDD.setName(dish.getName());
        dishFlavorDD.setCategoryId(dish.getCategoryId());
        dishFlavorDD.setPrice(dish.getPrice());
        dishFlavorDD.setCode(dish.getCode());
        dishFlavorDD.setImage(dish.getImage());
        dishFlavorDD.setDescription(dish.getDescription());
        dishFlavorDD.setStatus(dish.getStatus());
        dishFlavorDD.setSort(dish.getSort());
        dishFlavorDD.setCreateTime(dish.getCreateTime());
        dishFlavorDD.setUpdateTime(dish.getUpdateTime());
        dishFlavorDD.setCreateUser(dish.getCreateUser());
        dishFlavorDD.setUpdateUser(dish.getUpdateUser());
        
        List<DishFlavor> flavors = dishMapper.getFlavorsByDishId(id);
        dishFlavorDD.setFlavors(flavors);
        
        return R.success(dishFlavorDD);
    }

    @Override
    public R<List<Dish>> dishList(Dish dish) {
        List<Dish> dishList = dishMapper.dishList(dish);
        return R.success(dishList);
    }
}
