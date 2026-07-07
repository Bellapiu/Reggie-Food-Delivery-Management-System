package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
