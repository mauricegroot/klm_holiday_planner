package com.airfranceklm.fasttrack.assignment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createNewEmployeeShouldFail() throws Exception {
    Employee user = new Employee("randomId");
    user.setName("name");

    mockMvc.perform(post("/employees")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isConflict());
  }

  @Test
  void createNewEmployeeShouldSucceed() throws Exception {
    Employee user = new Employee("klm123456");
    user.setName("name");

    mockMvc.perform(post("/employees")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk());
  }

}
