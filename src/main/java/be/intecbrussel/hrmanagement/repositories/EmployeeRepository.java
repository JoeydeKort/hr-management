package be.intecbrussel.hrmanagement.repositories;

import be.intecbrussel.hrmanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 OR e.lastName = ?1")
    Employee findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.firstName =:firstName AND e.lastName =:lastName")
    Employee findByNameWithNamedParams(@Param("firstName") String firstName, @Param("lastName")String lastName);

}
