package be.intecbrussel.hrmanagement.services;

import be.intecbrussel.hrmanagement.models.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {

    Employee addEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional <Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee updatedEmployee, long id);
    void deleteEmployee(long id);
    Employee searchEmployee(String name);

}
