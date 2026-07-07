package com.example.lys01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lys01.entry.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
