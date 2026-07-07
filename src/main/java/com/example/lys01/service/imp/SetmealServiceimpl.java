package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.Setmeal;
import com.example.lys01.entry.SetmealDao;
import com.example.lys01.entry.SetmealDish;
import com.example.lys01.mapper.SetmealMapper;
import com.example.lys01.service.SetmealDishService;
import com.example.lys01.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceimpl extends ServiceImpl<SetmealMapper, SetmealDao> implements SetmealService {
    
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSetmeal(SetmealDao setmealDao) {
        if (setmealDao.getId() == null) {
            return false;
        }
        
        boolean updated = this.updateById(setmealDao);
        
        if (updated && setmealDao.getSetmealDishes() != null) {
            QueryWrapper<SetmealDish> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("setmeal_id", setmealDao.getId());
            setmealDishService.remove(queryWrapper);
            
            List<SetmealDish> dishes = setmealDao.getSetmealDishes();
            for (SetmealDish dish : dishes) {
                dish.setId(SnowflakeIdGenerator.getInstance().nextId());
                dish.setSetmealId(setmealDao.getId());
                setmealDishService.save(dish);
            }
        }
        
        return updated;
    }

    @Override
    public SetmealDao getSetmealWithDishes(Long id) {
        SetmealDao setmealDao = this.getById(id);
        if (setmealDao != null) {
            QueryWrapper<SetmealDish> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("setmeal_id", id);
            List<SetmealDish> dishes = setmealDishService.list(queryWrapper);
            setmealDao.setSetmealDishes(dishes);
        }
        return setmealDao;
    }
}
