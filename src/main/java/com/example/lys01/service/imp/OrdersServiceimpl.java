package com.example.lys01.service.imp;
import com.example.lys01.entry.Orders;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.mapper.OrdersMapper;
import com.example.lys01.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceimpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
