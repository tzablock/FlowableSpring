package com.springflowable.flowablespring.controller;

import com.springflowable.flowablespring.process.HolidayBookProcess;
import com.springflowable.flowablespring.process.UserInteraction;
import com.springflowable.flowablespring.service.ProcessService;
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
        UserInteraction pp = new UserInteraction();
        HolidayBookProcess hbp = new HolidayBookProcess(pp);

        Deployment deploy = processService.createDeployment("processes/holiday-request.bpmn");
        ProcessDefinition processDefinition = processService.getProcessDefinition(deploy.getId());
        System.out.println(processDefinition.getName());

        Map<String, Object> userInput = hbp.scanUserVariables();
        ProcessInstance holidayRequest = processService.startProcessInstance("holidayRequest", userInput);
        System.out.println(holidayRequest.getName());

        List<Task> tasks = processService.getTasksForCandidateGroup("managers");
        hbp.printTasks(tasks);
        String taskId = hbp.chooseTaskAndReturnId(tasks);
        Map<String,Object> taskVars = processService.getTaskVariables(taskId);
        hbp.presentRequest(taskVars);
        Map<String, Object> approvalParams = hbp.presentApprovalQuestion();
        processService.completeTask(taskId, approvalParams);

        List<HistoricActivityInstance> finishedTasks = processService.getFinishedHistoricTasks(holidayRequest.getId());
        hbp.presentFinishedHistoricTasks(finishedTasks);
    }
}
