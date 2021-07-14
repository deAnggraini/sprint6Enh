package id.co.bca.pakar.be.wf.service;

import id.co.bca.pakar.be.wf.dao.EmployeeRepository;
import id.co.bca.pakar.be.wf.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    // create the list of Employees into the database who perform the task
    public void createEmployee() {

        if (employeeRepository.findAll().size() == 0) {

            employeeRepository.save(new Employee("Prince", "Software Enginner"));
            employeeRepository.save(new Employee("Gaurav", "Technical Lead"));
            employeeRepository.save(new Employee("Abhinav", "Test Lead"));
        }
    }
}
