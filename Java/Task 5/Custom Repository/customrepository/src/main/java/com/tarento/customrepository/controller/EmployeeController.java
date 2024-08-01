package com.tarento.customrepository.controller;

import com.tarento.customrepository.dto.EmployeeDto;
import com.tarento.customrepository.model.Employee;
import com.tarento.customrepository.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/get")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/get/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/get/dept/{dept}")
    public List<Employee> getEmployeeByDepartment(@PathVariable String dept) {
        return employeeService.findEmployeeByDepartment(dept);
    }

    @GetMapping("/get/year/{year}")
    public List<Employee> getEmployeeByYear(@PathVariable int year) {
        return employeeService.findEmployeeByYear(year);
    }

    @GetMapping("/get/{year}/{dept}")
    public List<Employee> getEmployeeByYearAndDepartment(@PathVariable int year, @PathVariable String dept) {
        return employeeService.findEmployeeByYearAndDepartment(year, dept);
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@RequestBody EmployeeDto employeeDto) {
        String name = employeeDto.getName();
        String dept = employeeDto.getDept();
        employeeService.addEmployee(name, dept);
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        String name = employeeDto.getName();
        String dept = employeeDto.getDept();
        employeeService.updateEmployee(id, name, dept);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }
}
