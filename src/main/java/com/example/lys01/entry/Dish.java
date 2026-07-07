package com.example.lys01.entry;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 菜品
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;


    //菜品名称
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //菜品分类id
    private Long categoryId;


    //菜品价格
    private BigDecimal price;


    //商品码
    private String code;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;


    //顺序
    private Integer sort;



    private LocalDateTime createTime;



    private LocalDateTime updateTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUser;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updateUser;

    private Integer isDeleted;





}
