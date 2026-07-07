package com.example.lys01.controller;

import com.example.lys01.Result.R;
import com.example.lys01.common.SnowflakeIdGenerator;
import com.example.lys01.entry.Employee;
import com.example.lys01.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/employee")
@RestController
public class  EmployeeController {
    @Autowired
      
    private EmployeeService employeeService;
    @RequestMapping("/login")
   public R<Map<String, Object>> login(@RequestBody Employee employee, HttpServletRequest r){
        R<Employee> res=employeeService.login(employee);
        if(res.getCode() == 1){
            r.getSession().setAttribute("employee",res.getData());
            String redirectUrl = (String) r.getSession().getAttribute("redirectUrl");
            Map<String, Object> data = new java.util.HashMap<>();
            data.put("employee", res.getData());
            data.put("redirectUrl", redirectUrl);
            r.getSession().removeAttribute("redirectUrl");
            return R.success(data);
        }
        return R.error(res.getMsg());
    }
    //退出登录
    @RequestMapping("/logout")
    public R<String> logout(HttpServletRequest r){
        r.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    //获取员工列表
    @GetMapping("/page")
    public R<Map<String,Object>> getEmployeeList(Integer page,Integer pageSize,String name){
        return employeeService.getEmployeeList(page,pageSize,name);
    }
    //添加员工
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee,HttpServletRequest request){
        Employee dengluren=(Employee)request.getSession().getAttribute("employee");
        if(dengluren == null){
            return R.error("用户未登录或登录已过期");
        }
        
        employee.setId(SnowflakeIdGenerator.getInstance().nextId());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setStatus(1);
        
        Long user=dengluren.getId();
        employee.setCreateUser(user);
        employee.setUpdateUser(user);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        return employeeService.addEmployee(employee);
    }
    //修改员工状态
    @PutMapping("/status")
    public R<String> updateStatus(@RequestBody Employee employee,HttpServletRequest request){
        System.out.println("===== 修改员工状态 =====");
        System.out.println("员工ID: " + employee.getId());
        System.out.println("状态: " + employee.getStatus());
        
        Employee dengluren=(Employee)request.getSession().getAttribute("employee");
        if(dengluren == null){
            return R.error("用户未登录或登录已过期");
        }
        
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(dengluren.getId());
        R<String> result = employeeService.updateStatus(employee);
        System.out.println("修改结果: " + result.getMsg());
        return result;
    }
    //修改员工信息
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){
        Employee dengluren=(Employee)request.getSession().getAttribute("employee");
        if(dengluren == null){
            return R.error("用户未登录或登录已过期");
        }
        
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(dengluren.getId());
        return employeeService.update(employee);
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        return employeeService.getById(id);
    }


}
