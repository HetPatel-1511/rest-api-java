package com.example.restapi.repos;

import com.example.restapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username")
//    Boolean existsByUsername(String username);
    boolean existsByUsername(String username);
    Employee findByUsername(String username);

}
