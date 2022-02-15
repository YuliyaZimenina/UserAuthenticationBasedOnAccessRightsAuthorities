package net.juliya.zimenina.UserAuthenticationBasedOnAccessRightsAuthorities.rest;


import net.juliya.zimenina.UserAuthenticationBasedOnAccessRightsAuthorities.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeRestControllerV1 {

    // Collection to simulate a database
    private List<Employee> EMPLOYEES = Stream.of(
            new Employee(1L, "Sam", "Sailor", "Developer"),
            new Employee(2L, "David", "Nolan", "Developer"),
            new Employee(3L, "Ruby", "Red", "QA"),
            new Employee(4L, "Ben", "Taylor", "QA"),
            new Employee(5L, "Sandra", "Peters", "PM")
    ).collect(Collectors.toList());


    //Method that return a list of all employees
    @GetMapping
    public List<Employee> allEmployees(){
        return EMPLOYEES;
    }

    // Search method by id
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id){
        return EMPLOYEES.stream().filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //Method for adding an employee to the list
    @PostMapping
    public Employee create(@RequestBody Employee employee){
        this.EMPLOYEES.add(employee);
        return employee;
    }

    //Method for removing an employee from the list
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.EMPLOYEES.removeIf(employee -> employee.getId().equals(id));
    }
}
