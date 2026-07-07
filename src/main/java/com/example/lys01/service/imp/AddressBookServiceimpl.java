package com.example.lys01.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lys01.entry.AddressBook;
import com.example.lys01.mapper.AddressBookMapper;
import com.example.lys01.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceimpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
