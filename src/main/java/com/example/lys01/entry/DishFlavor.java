package com.example.lys01.entry;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
菜品口味
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //菜品id
    private Long dishId;


    //口味名称
    private String name;


    //口味数据list
    private String value;



    private LocalDateTime createTime;



    private LocalDateTime updateTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUser;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}
