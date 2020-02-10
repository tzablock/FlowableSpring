package com.springflowable.flowablespring.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ExternalSystemCall implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println(String.format("Calling external system for execution adding employee: %s holidays.", delegateExecution.getVariable("employeeName")));
    }
}
