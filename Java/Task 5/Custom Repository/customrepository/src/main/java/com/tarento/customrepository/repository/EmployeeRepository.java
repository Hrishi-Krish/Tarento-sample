package com.tarento.customrepository.repository;

import com.tarento.customrepository.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("Select e from Employee e where e.dept = ?1")
    List<Employee> getEmployeesByDepartment( String dept);

    @Query(value = "Select * from Employee e where EXTRACT(YEAR from e.hire_date) = ?1", nativeQuery = true)
    List<Employee> getEmployeeByYear(int year);

    @Query(value = "Select * from Employee e where EXTRACT(YEAR from e.hire_date) = ?1 and e.dept = ?2", nativeQuery = true)
    List<Employee> getEmployeeByYearAndDepartment(int year, String dept);
}
