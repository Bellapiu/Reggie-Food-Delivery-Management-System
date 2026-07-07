package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lys01.Result.R;

import com.example.lys01.entry.ShoppingCart;
import com.example.lys01.entry.User;
import com.example.lys01.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController  {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpServletRequest request){
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userId!=null,"user_id",userId);
        return R.success(shoppingCartService.list(queryWrapper));

    }
    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        shoppingCart.setUserId(userId);
        if(userId!=null){
            QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userId);
            if (shoppingCart.getDishId()!=null)
                queryWrapper.eq("dish_id",shoppingCart.getDishId());
            if(shoppingCart.getSetmealId()!=null)
                queryWrapper.eq("setmeal_id",shoppingCart.getSetmealId());

            ShoppingCart chaart = shoppingCartService.getOne(queryWrapper);
            if(chaart!=null){
                chaart.setNumber(chaart.getNumber()+1);
                shoppingCartService.updateById(chaart);
            }
            else{
                shoppingCart.setNumber(1);
                shoppingCartService.save(shoppingCart);
            }
        }
        return R.success("添加成功");
    }
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        if(userId!=null){
            QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userId);
            if (shoppingCart.getDishId()!=null)
                queryWrapper.eq("dish_id",shoppingCart.getDishId());
            if(shoppingCart.getSetmealId()!=null)
                queryWrapper.eq("setmeal_id",shoppingCart.getSetmealId());

            ShoppingCart chaart = shoppingCartService.getOne(queryWrapper);
            if(chaart!=null){
                if(chaart.getNumber()>1){
                    chaart.setNumber(chaart.getNumber() - 1);
                    shoppingCartService.updateById(chaart);
                }
                else{
                    shoppingCartService.removeById(chaart);
                }
                return R.success("删除成功");
            }
            else {
                return R.error("删除失败");
            }
        }
        return R.error("用户没有登录");
    }
    @DeleteMapping("/clean")
    public R<String> clean(HttpServletRequest request){
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        shoppingCartService.remove(queryWrapper);
        return R.success("清空成功");
    }

}
