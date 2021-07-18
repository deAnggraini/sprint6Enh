package id.co.bca.pakar.be.wf.service;

import org.springframework.stereotype.Service;

@Service
public class ProcessService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private RepositoryService repositoryService;
//
//    // start the process and set employee as variable
//    public String startTheProcess(String assignee) {
//
//        Employee employee = employeeRepository.findByName(assignee);
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("employee", employee);
//
//        runtimeService.startProcessInstanceByKey("simple-process", variables);
//
//        return processInformation();
//    }
//
//    // fetching process and task information
//    public String processInformation() {
//
//        List<Task> taskList = taskService.createTaskQuery().orderByTaskCreateTime().asc().list();
//
//        StringBuilder processAndTaskInfo = new StringBuilder();
//
//        processAndTaskInfo.append("Number of process definition available: "
//                + repositoryService.createProcessDefinitionQuery().count() + " | Task Details= ");
//
//        taskList.forEach(task -> {
//
//            processAndTaskInfo.append("ID: " + task.getId() + ", Name: " + task.getName() + ", Assignee: "
//                    + task.getAssignee() + ", Description: " + task.getDescription());
//        });
//
//        return processAndTaskInfo.toString();
//    }
//
//    // fetch task assigned to employee
//    public List<Task> getTasks(String assignee) {
//        return taskService.createTaskQuery().taskAssignee(assignee).list();
//    }
//
//    // complete the task
//    public void completeTask(String taskId) {
//        taskService.complete(taskId);
//    }
}
