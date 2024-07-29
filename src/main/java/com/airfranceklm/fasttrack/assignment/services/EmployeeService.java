package com.airfranceklm.fasttrack.assignment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return employeeRepository.findById(id).get();
    }

    public Employee saveEmployee(Employee employee) throws Exception {
        if(employeeRepository.existsById(employee.getId())) {
            throw new Exception("Employee already exists");
        }
        return employeeRepository.save(employee);
    }

}
