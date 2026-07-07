package com.example.lys01.service;

import com.example.lys01.Result.R;
import com.example.lys01.entry.Employee;

import java.util.Map;

public interface EmployeeService {
    R<Employee> login(Employee employee);

    R<Map<String, Object>> getEmployeeList(Integer page, Integer pageSize, String name);

    R<String> addEmployee(Employee employee);


    R<String> updateStatus(Employee employee);


    R<String> update(Employee employee);

    R<Employee> getById(Long id);
}
