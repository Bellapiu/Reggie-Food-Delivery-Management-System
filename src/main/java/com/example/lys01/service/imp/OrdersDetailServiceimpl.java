package com.example.lys01.service.imp;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.entry.OrderDetail;
import com.example.lys01.mapper.OrdersDetailMapper;
import com.example.lys01.service.OrdersDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrdersDetailServiceimpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrdersDetailService {
}
