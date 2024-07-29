package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.services.EmployeeService;
import com.airfranceklm.fasttrack.assignment.services.HolidayService;

@RestController
@CrossOrigin(origins = "http://localhost:5173") //allow from Vite (for testing)
public class EmployeeApi {
    
    @Autowired
	private HolidayService holidayService;
    @Autowired
	private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.list();
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        try {
            return employeeService.saveEmployee(employee);
        }catch(Exception exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "cannot add employee: " + exc.getMessage(), exc);
        }
    }

    @GetMapping("/employees/{id}/holidays")
    public List<Holiday> getHolidaysByEmployeeId(@PathVariable String id) {
        return holidayService.listByEmployeeId(id);
    }
}
