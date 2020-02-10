package com.springflowable.flowablespring.service;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    public Deployment createDeployment(String procDefFileName){
        return repositoryService.createDeployment()
                .addClasspathResource(procDefFileName)
                .deploy();
    }

    public ProcessDefinition getProcessDefinition(String deploymentId){
        return repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
    }

    public ProcessInstance startProcessInstance(String processName, Map<String, Object> variables){
        return runtimeService.startProcessInstanceByKey(processName, variables);
    }

    public List<Task> getTasksForCandidateGroup(String candidateGroup){
        return taskService.createTaskQuery().taskCandidateGroup(candidateGroup).list();
    }

    public Map<String, Object> getTaskVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    public void completeTask(String taskId, Map<String, Object> approvalParams) {
        taskService.complete(taskId, approvalParams);
    }

    public List<HistoricActivityInstance> getFinishedHistoricTasks(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                  .processInstanceId(processInstanceId)
                  .finished()
                  .orderByHistoricActivityInstanceEndTime().asc()
                  .list();
    }
}
