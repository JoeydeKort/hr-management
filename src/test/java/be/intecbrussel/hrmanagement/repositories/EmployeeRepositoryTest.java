package be.intecbrussel.hrmanagement.repositories;

import be.intecbrussel.hrmanagement.models.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository;
    private Employee employee;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Jane", "Doe", "jane@doe.com");
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    // BDD -> Behavioral driven development
    @Test
    public void given_EmployeeObjwhen_UpdateEmployeethen_ReturnUpdatedEmployeeObj() {

        // given
        employeeRepository.save(employee);
        System.out.println(employee);
        // when
        Employee savedEmployee = employeeRepository.findByEmail(employee.getEmail()).get();
        savedEmployee.setFirstName("Zoë");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        System.out.println(savedEmployee);
        // then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Zoë");
    }

    @Test
    public void givenEmployeEmail_whenFindByEmail_then_ReturnEmployeeObj() {

        // given
        employeeRepository.save(employee);
        // when
        Employee foundEmployee = employeeRepository.findByEmail(employee.getEmail()).get();
        // then
        assertThat(foundEmployee).isNotNull();
    }

    @Test
    public void givenEmployeeName_whenFindByName_then_ReturnEmployeeObj() {

        // given
        employeeRepository.save(employee);
        String firstname = employee.getFirstName();
        // when
        Employee foundEmployee = employeeRepository.findByName(firstname);
        System.out.println(foundEmployee);
        // then
        assertThat(foundEmployee).isNotNull();

    }


}