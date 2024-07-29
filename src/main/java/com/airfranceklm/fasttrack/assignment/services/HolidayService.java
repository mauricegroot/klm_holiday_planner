package com.airfranceklm.fasttrack.assignment.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.repositories.HolidayRepository;

import jakarta.el.MethodNotFoundException;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    private static final int MIN_DAYS_IN_ADVANCE_CREATE = 5;
    private static final int MIN_DAYS_IN_ADVANCE_DELETE = 5;
    private static final int MIN_DAYS_BETWEEN = 3;

    public Holiday getById(String id) {
        return holidayRepository.findById(id).get();
    }

    public List<Holiday> list() {
        return holidayRepository.findAll();
    }

    public List<Holiday> listByEmployeeId(String employeeId) {
        return holidayRepository.findByEmployee(new Employee(employeeId));
    }

    public Holiday saveHoliday(Holiday holiday) throws Exception {
        if(holiday.getLabel().isEmpty() || holiday.getStartDate() == null || holiday.getEndDate() == null || holiday.getEmployee()== null) {
            throw new Exception("Label cannot be empty");
        }
        if(holiday.getStartDate().isAfter(holiday.getEndDate())) {
            throw new Exception("Start date cannot be after end date");
        }
        if((LocalDate.now()).datesUntil(holiday.getStartDate()).count() < MIN_DAYS_IN_ADVANCE_CREATE) {
            throw new Exception("Less than " + MIN_DAYS_IN_ADVANCE_CREATE + " days before start");
        }

        List<Holiday> holidaysDaysBetween = holidayRepository.findWithDaysBetween(holiday.getStartDate().minusDays(MIN_DAYS_BETWEEN), holiday.getEndDate().plusDays(MIN_DAYS_BETWEEN), holiday.getEmployee());
        if(!holidaysDaysBetween.isEmpty()) {
            throw new Exception("No " + MIN_DAYS_BETWEEN + " days between");
        }

        List<Holiday> holidaysOverlapping = holidayRepository.findWithOverlappingDates(holiday.getStartDate(), holiday.getEndDate());
        if(!holidaysOverlapping.isEmpty()) {
            throw new Exception("Overlapping with " + holidaysOverlapping.size() + " other holiday(s)");
        }

        return holidayRepository.save(holiday);
    }

    public Holiday updateHoliday(String holidayId, Holiday holiday) {
        throw new MethodNotFoundException("to be implemented");
    }

    public void deleteHoliday(String holidayId) throws Exception {
        Holiday checkHoliday = holidayRepository.getReferenceById(holidayId);
        if((LocalDate.now()).datesUntil(checkHoliday.getStartDate()).count() < MIN_DAYS_IN_ADVANCE_DELETE) {
            throw new Exception("Less than " + MIN_DAYS_IN_ADVANCE_CREATE + " days before cancellation");
        }

        holidayRepository.deleteById(holidayId);
    }

}
