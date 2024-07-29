package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.services.EmployeeService;
import com.airfranceklm.fasttrack.assignment.services.HolidayService;

@RestController
@CrossOrigin(origins = "http://localhost:5173") //allow from Vite (for testing)
public class HolidaysApi {

    @Autowired
	private HolidayService holidayService;
    @Autowired
	private EmployeeService employeeService;
    
    @GetMapping("/holidays")
    public List<Holiday> getHolidays() {
        return holidayService.list();
    }

    @PostMapping("/holidays")
    public Holiday saveHoliday(@RequestBody Holiday holiday) {
        try {
            //get valid Employee and set it on the Holiday
            Employee employee = employeeService.getById(holiday.getEmployee().getId());
            holiday.setEmployee(employee);
            return holidayService.saveHoliday(holiday);
        }catch(Exception exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "cannot add holiday: " + exc.getMessage(), exc);
        }
    }

	@PutMapping("holidays/{id}")
	public Holiday updateHoliday(@PathVariable String id, @RequestBody Holiday entity) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "To be implemented");
	}

	@DeleteMapping("holidays/{id}")
	public String deleteHoliday(@PathVariable String id) {
        try {
            holidayService.deleteHoliday(id);
            return "Holiday deleted";
        }catch(Exception exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exc.getMessage(), exc);
        }
	}

}
