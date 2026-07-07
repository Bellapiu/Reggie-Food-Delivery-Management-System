package com.example.lys01.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("setmeal")
public class SetmealDao extends Setmeal{
    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private List<SetmealDish> setmealDishes;
}
