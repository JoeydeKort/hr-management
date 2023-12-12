package be.intecbrussel.hrmanagement.services.impl;

import be.intecbrussel.hrmanagement.models.Employee;
import be.intecbrussel.hrmanagement.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Jane", "Doe", "jane@doe.com");
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObj_whenAddEmployee_thenReturnEmployeeObj_() {
        // given
        //given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.addEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
    }


}