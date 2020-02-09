package com.flowable.spring;

import com.flowable.process.HolidayBookProcess;
import com.flowable.process.UserInteraction;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProcessRestController {

    @Autowired
    ProcessService processService;

    @GetMapping("/")
    public void startProcess(){
        com.flowable.standalone.ProcessService ps = new com.flowable.standalone.ProcessService();
        UserInteraction pp = new UserInteraction();
        HolidayBookProcess hbp = new HolidayBookProcess(pp);

        Deployment deploy = ps.createDeployment("processes/holiday-request.bpmn");
        ProcessDefinition processDefinition = ps.getProcessDefinition(deploy.getId());
        System.out.println(processDefinition.getName());

        Map<String, Object> userInput = hbp.scanUserVariables();
        ProcessInstance holidayRequest = ps.startProcessInstance("holidayRequest", userInput);
        System.out.println(holidayRequest.getName());

        List<Task> tasks = ps.getTasksForCandidateGroup("managers");
        hbp.printTasks(tasks);
        String taskId = hbp.chooseTaskAndReturnId(tasks);
        Map<String,Object> taskVars = ps.getTaskVariables(taskId);
        hbp.presentRequest(taskVars);
        Map<String, Object> approvalParams = hbp.presentApprovalQuestion();
        ps.completeTask(taskId, approvalParams);

        List<HistoricActivityInstance> finishedTasks = ps.getFinishedHistoricTasks(holidayRequest.getId());
        hbp.presentFinishedHistoricTasks(finishedTasks);
    }
}
