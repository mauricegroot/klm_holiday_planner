package com.airfranceklm.fasttrack.assignment.resources;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Holiday {

    public enum Status {
        DRAFT, REQUESTED, SCHEDULED, ARCHIVED
    }

    @Id
    @UuidGenerator
    private String id;
    @NotBlank
    private String label;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @NotBlank
    private String status = "DRAFT";

    @ManyToOne()
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Holiday(String label) {
        this.label = label;
    }

    public Holiday() {

    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String holidayLabel) {
        this.label = holidayLabel;
    }

    public void setId(String holidayId) {
        this.id = holidayId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
