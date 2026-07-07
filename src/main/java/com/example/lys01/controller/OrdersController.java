package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lys01.Result.R;
import com.example.lys01.entry.Orders;
import com.example.lys01.entry.User;
import com.example.lys01.service.OrdersDetailService;
import com.example.lys01.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersDetailService ordersDetailService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders, HttpServletRequest request){
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        orders.setUserId(userId);
        orders.setStatus(2);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(BigDecimal.valueOf(100));
        ordersService.save(orders);


        return R.success("下单成功");

    }


    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize, HttpServletRequest request){
        Page pageInfo = new Page(page,pageSize);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        queryWrapper.eq("user_id",userId);

        return R.success(ordersService.page(pageInfo,queryWrapper));

    }
    //http://localhost:8088/order/page?page=1&pageSize=10
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page pageInfo = new Page(page,pageSize);
        return R.success(ordersService.page(pageInfo));
    }
}
