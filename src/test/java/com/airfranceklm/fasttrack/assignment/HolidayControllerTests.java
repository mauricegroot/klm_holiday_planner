package com.airfranceklm.fasttrack.assignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.repositories.HolidayRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class HolidayControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private HolidayRepository holidayRepository;

  @Test
  void createNewHolidayShouldSucceed() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now().plusDays(10));
    holiday.setEndDate(LocalDate.now().plusDays(15));
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
  }

  @Test
  void createNewHolidayShouldFail_emptyDates() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isConflict());
  }

  @Test
  void createNewHolidayShouldFail_emptyLabel() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setStartDate(LocalDate.now());
    holiday.setEndDate(LocalDate.now());
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isConflict());
  }

  @Test
  void createNewHolidayShouldFail_emptyEmployee() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now());
    holiday.setEndDate(LocalDate.now());

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isConflict());
  }

  @Test
  void createNewHolidayShouldFail_withinFiveDays() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now());
    holiday.setEndDate(LocalDate.now());
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isConflict());
  }

  @Test
  void createNewHolidayShouldFail_overlapping() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now().plusDays(10));
    holiday.setEndDate(LocalDate.now().plusDays(15));
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isOk());

    holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now().plusDays(11));
    holiday.setEndDate(LocalDate.now().plusDays(14));
    holiday.setEmployee(new Employee("klm012345"));

    mockMvc.perform(post("/holidays")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(holiday)))
            .andExpect(status().isConflict());
  }

  @Test
  void deleteHolidayShouldSucceed() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now().plusDays(10));
    holiday.setEndDate(LocalDate.now().plusDays(15));
    holiday.setEmployee(new Employee("klm012345"));

    Holiday savedHoliday = holidayRepository.save(holiday);

    //holiday = someClass;

    mockMvc.perform(delete("/holidays/"+savedHoliday.getId()))
            .andExpect(status().isOk());
  }

  @Test
  void deleteHolidayShouldFail_withinFiveDays() throws Exception {
    Holiday holiday = new Holiday();
    holiday.setLabel("test");
    holiday.setStartDate(LocalDate.now().plusDays(2));
    holiday.setEndDate(LocalDate.now().plusDays(3));
    holiday.setEmployee(new Employee("klm012345"));

    Holiday savedHoliday = holidayRepository.save(holiday);


    mockMvc.perform(delete("/holidays/"+savedHoliday.getId()))
            .andExpect(status().isConflict());
  }

  @AfterEach
    public void cleanup() {
        holidayRepository.deleteAll();
    }

}
