package com.airfranceklm.fasttrack.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airfranceklm.fasttrack.assignment.resources.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
}
