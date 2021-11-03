package springboot.projectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.projectbackend.exception.Exception;
import springboot.projectbackend.model.Employee;
import springboot.projectbackend.repository.EmployeeRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/start")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new Exception("Employee is not exist"));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") long id, @Valid @RequestBody Employee data){
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee is not exist"));
        updateEmployee.setFirstName(data.getFirstName());
        updateEmployee.setLastName(data.getLastName());
        updateEmployee.setEmail(data.getEmail());

        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(value="id") long id, @Valid @RequestBody Employee data){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new Exception("Employee is not exist"));
        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
