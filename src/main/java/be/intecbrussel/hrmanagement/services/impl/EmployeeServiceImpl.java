package be.intecbrussel.hrmanagement.services.impl;

import be.intecbrussel.hrmanagement.exceptions.ResourceNotFoundException;
import be.intecbrussel.hrmanagement.models.Employee;
import be.intecbrussel.hrmanagement.repositories.EmployeeRepository;
import be.intecbrussel.hrmanagement.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee", "employee", employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {

        return Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employee", id)));

    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employee", employee.getId()));

        foundEmployee.setFirstName(employee.getFirstName());
        foundEmployee.setLastName(employee.getLastName());
        foundEmployee.setEmail(employee.getEmail());

        return employeeRepository.save(foundEmployee);
    }

    @Override
    public void deleteEmployee(long id) {

        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employee", id ));

        employeeRepository.deleteById(id);
    }

    @Override
    public Employee searchEmployee(String name) {
        return employeeRepository.findByName(name);
    }


}
