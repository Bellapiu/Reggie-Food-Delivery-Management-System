package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
