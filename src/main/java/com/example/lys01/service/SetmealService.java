package com.example.lys01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lys01.entry.Setmeal;
import com.example.lys01.entry.SetmealDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<SetmealDao> {
    
    @Transactional
    boolean updateSetmeal(SetmealDao setmealDao);
    
    SetmealDao getSetmealWithDishes(Long id);
}
