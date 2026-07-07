package com.example.lys01.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lys01.Result.R;
import com.example.lys01.common.ValidateCodeUtils;
import com.example.lys01.entry.User;
import com.example.lys01.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
        //提取出界面输入的手机号
        String phone = user.getPhone();

        String yz= ValidateCodeUtils.generateValidateCode(4)+"";
        System.out.println("*****************"+yz);
        //将验证码保存到Session中
        request.getSession().setAttribute("code",yz);
        return R.success("发送成功");


    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map, HttpServletRequest request) {
        //提取出界面输入的手机号
        String phone = map.get("phone");
        String code = map.get("code");

        //判断验证码是否正确
        String codeInSession = (String) request.getSession().getAttribute("code");
        if (codeInSession != null && codeInSession.equals(code)) {
            //手机号用户是否存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            request.getSession().setAttribute("user", user);
            return R.success(user);
        } else {
            return R.error("验证码错误");
        }

    }
}
