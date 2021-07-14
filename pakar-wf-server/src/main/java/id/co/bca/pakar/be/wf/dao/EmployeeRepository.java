package id.co.bca.pakar.be.wf.dao;

import id.co.bca.pakar.be.wf.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Employee findByName(String name);
}
