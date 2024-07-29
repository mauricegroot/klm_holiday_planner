package com.airfranceklm.fasttrack.assignment.resources;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Employee {

    @Id
    @Pattern(regexp = "^klm[0-9]{6}$")
    private String id;
    @NotBlank
    private String name;

    public Employee(String id) {
        this.id = id;
    }

    public Employee() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String employeeId) {
        this.id = employeeId;
    }
    
}
