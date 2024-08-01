package com.tarento.customrepository.service;


import com.tarento.customrepository.model.Employee;
import com.tarento.customrepository.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    public List<Employee> findAllEmployees() {
        return empRepo.findAll();
    }

    public Employee findEmployeeById(long id) {
        Optional<Employee> emp = empRepo.findById(id);
        if (emp.isEmpty()) {
            throw new RuntimeException("Employee with id " + id + " not found");
        }
        return emp.get();
    }

    public List<Employee> findEmployeeByDepartment(String dept) {
        return empRepo.getEmployeesByDepartment(dept);
    }

    public List<Employee> findEmployeeByYear(int year) {
        return empRepo.getEmployeeByYear(year);
    }

    public List<Employee> findEmployeeByYearAndDepartment(int year, String dept) {
        return empRepo.getEmployeeByYearAndDepartment(year, dept);
    }

    public void addEmployee(String name, String dept) {
        Employee emp = new Employee(name, dept);
        empRepo.save(emp);
    }

    public void updateEmployee(long id, String name, String dept) {
        try {
            Employee emp = findEmployeeById(id);
            emp.setName(name);
            emp.setDept(dept);

            empRepo.save(emp);
        } catch (Exception e) {
            throw new RuntimeException("Employee with id " + id + " not found");
        }
    }

    public void deleteEmployee(long id) {
        empRepo.deleteById(id);
    }

}
