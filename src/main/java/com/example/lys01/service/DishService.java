package com.example.lys01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lys01.Result.R;
import com.example.lys01.entry.Dish;
import com.example.lys01.entry.DishFlavorDD;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DishService extends IService<DishFlavorDD> {
    R<Map<String, Object>> dishFind(int page, int pageSize);

    R<Map<String, Object>> dishFindByName(int page, int pageSize, String name);

    @Transactional
    R<String> dishAdd(DishFlavorDD dishFlavorDD);

    R<String> dishUpdate(Dish dish);

    R<String> dishDelete(Long id);

    @Transactional
    R<String> dishDeleteBatch(List<Long> ids);

    @Transactional
    R<String> dishUpdateStatusBatch(List<Long> ids, Integer status);

    R<DishFlavorDD> dishGetById(Long id);

    R<List<Dish>> dishList(Dish dish);
}
