package com.example.lys01.service.imp;

import com.example.lys01.Result.R;
import com.example.lys01.entry.Employee;
import com.example.lys01.mapper.EmployeeMapper;
import com.example.lys01.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceimpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(Employee employee) {
        Employee emp = employeeMapper.getByUsername(employee.getUsername());
//        用户名是否存在
        if(emp == null)
            return R.error("用户不存在");
//        密码是否正确
//        前段密码进行加密，再和数据库密码比较
        String pw= DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if(!emp.getPassword().equals(pw))
            return R.error("密码错误");
//        状态是否禁用
        if(emp.getStatus() == 0)
            return R.error("用户已禁用");
        return R.success(emp);
    }
    //获取员工列表
    @Override
    public R<Map<String, Object>> getEmployeeList(Integer page, Integer pageSize, String name) {
        Map<String,Object> map = new HashMap<>();
        List<Employee> ls= new ArrayList<>();
        //根据名字获取
        if (name!=null){
            ls=employeeMapper.getByName(name,(page-1)*pageSize,pageSize);
            map.put("total",ls.size());
            map.put("records",ls);
        }
        //获取所有员工
        else {
            ls=employeeMapper.getAll((page-1)*pageSize,pageSize);
            map.put("total",ls.size());
            map.put("records",ls);
        }
        return R.success(map);
    }

    @Override
    public R<String> addEmployee(Employee employee) {
        Employee emp = employeeMapper.getByUsername(employee.getUsername());
        if(emp != null){
            return R.error("用户名已存在");
        }

        employeeMapper.addEmployee(employee);
        return R.success("添加员工成功");
    }

    @Override
    public R<String> updateStatus(Employee employee) {
        System.out.println("Service层接收到的参数 - ID: " + employee.getId() + ", Status: " + employee.getStatus());
        int n = employeeMapper.updateStatus(employee);
        System.out.println("更新的行数: " + n);
        if(n>0)
            return R.success("修改员工状态成功");
        else
            return R.error("修改员工状态失败");

    }
    //修改员工信息

    @Override
    public R<String> update(Employee employee) {
        int n=employeeMapper.update(employee);
        if(n>0)
            return R.success("修改员工信息成功");
        else
            return R.error("修改员工信息失败");
    }

    @Override
    public R<Employee> getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return R.success(employee);
    }



}





