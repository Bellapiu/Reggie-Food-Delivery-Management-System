package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.entry.DishFlavor;
import com.example.lys01.mapper.DishFlavorMapper;
import com.example.lys01.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceimpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
