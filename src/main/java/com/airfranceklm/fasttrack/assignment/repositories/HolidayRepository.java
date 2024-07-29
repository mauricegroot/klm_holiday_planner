package com.airfranceklm.fasttrack.assignment.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, String> {

    List<Holiday> findByEmployee(Employee employee);

    @Query("select h from Holiday h where h.employee=?3 AND ((?1 BETWEEN h.startDate AND h.endDate) OR (?2 BETWEEN h.startDate AND h.endDate) OR (h.startDate BETWEEN ?1 AND ?2) OR (h.endDate BETWEEN ?1 AND ?2))")
    List<Holiday> findWithDaysBetween(LocalDate startDate, LocalDate endDate, Employee e);

    @Query("select h from Holiday h where (h.startDate BETWEEN ?1 AND ?2) OR (h.endDate BETWEEN ?1 AND ?2) OR (?1 BETWEEN h.startDate AND h.endDate) OR (?2 BETWEEN h.startDate AND h.endDate)")
    List<Holiday> findWithOverlappingDates(LocalDate startDate, LocalDate endDate);
}
