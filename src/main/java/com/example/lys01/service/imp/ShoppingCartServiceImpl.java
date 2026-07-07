package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.entry.ShoppingCart;
import com.example.lys01.mapper.ShoppingCartMapper;
import com.example.lys01.service.ShoppingCartService;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{
}
