package be.intecbrussel.hrmanagement.controllers;

import be.intecbrussel.hrmanagement.models.Employee;
import be.intecbrussel.hrmanagement.services.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// @SpringBootTest

@WebMvcTest
class EmployeeControllerTest {

    private Employee employee;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "Jimmy", "Doe", "jimmy@doe.com");
    }

    @Test
    public void givenEmployeeObj_whenCreatingEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        given(employeeService.addEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then -> import static org.hamcrest.CoreMatchers.is;
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    public void givenListEmployees_whenGetAllEmployees_thenReturningEmployeeList() throws Exception {
        // given
        Employee employee1 = new Employee(2L, "Moustapha", "Doe", "Moustapha@doe.com");
        List<Employee> employeeList = List.of(employee, employee1);
        given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when
        ResultActions respons = mockMvc.perform(get("/employees/getAll"));

        // then
        respons.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));

    }

    @Test
    public void givenEmployeeObj_whenGetEmployeeById_thenEmployeeObj() throws Exception {
        // given
        given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.of(employee));

        // when
        ResultActions response = mockMvc.perform(get("/employees/get/{id}",
                employee.getId()));

        // then isOk = 200 http
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenInvalidEmployeeObj_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given
        given(employeeService.getEmployeeById(employee.getId()))
                .willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/employees/get/{id}",
                employee.getId()));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenEmployeeObj_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        // given
        Employee updatedEmployee = new Employee(1L, "Jane", "Doe", "jana@doe.com");
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class), any(Long.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions respons = mockMvc.perform(put("/employees/update/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        respons.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));

    }

    @Test
    public void givenEmployeeId_whenDeletingEmployee_thenReturningStatus200() throws Exception {
        // given
        willDoNothing().given(employeeService).deleteEmployee(employee.getId());

        // when
        ResultActions respons = mockMvc.perform(delete("/employees/delete/{id}", employee.getId()));

        // then
        respons.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("Employee deleted successfully, with id: " + employee.getId()));
    }

}