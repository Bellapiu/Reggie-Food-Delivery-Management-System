package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lys01.Result.R;
import com.example.lys01.entry.AddressBook;
import com.example.lys01.entry.User;
import com.example.lys01.service.AddressBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    
    @GetMapping("/default")
    public R<AddressBook> setDefault(HttpServletRequest request) {
        Long user_id = ((User) request.getSession().getAttribute("user")).getId();
        QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id).eq("is_default", 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (addressBook == null) {
            return R.error("没有默认地址");
        }
        return R.success(addressBook);
    }

    @PostMapping
    public R<String> save( HttpServletRequest request,@RequestBody AddressBook addressBook) {
        Long user_id = ((User) request.getSession().getAttribute("user")).getId();
        addressBook.setUserId(user_id);
        addressBook.setIsDefault(0);
        addressBook.setIsDeleted(0);
        addressBookService.save(addressBook);
        return R.success("保存成功");
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list(HttpServletRequest request) {
    Long user_id = ((User) request.getSession().getAttribute("user")).getId();
    QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", user_id) ;
    queryWrapper.eq("is_deleted", 0);
    return R.success(addressBookService.list(queryWrapper));

    }

    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook, HttpServletRequest request) {
        Long user_id = ((User) request.getSession().getAttribute("user")).getId();
        
        QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id);
        
        AddressBook updateAll = new AddressBook();
        updateAll.setIsDefault(0);
        addressBookService.update(updateAll, queryWrapper);
        
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        
        return R.success("设置默认地址成功");
    }
}